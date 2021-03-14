![Scala CI](https://github.com/a-khakimov/tinvest4s/workflows/Scala%20CI/badge.svg?branch=main)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=a-khakimov_tinvest4s&metric=ncloc)](https://sonarcloud.io/dashboard?id=a-khakimov_tinvest4s)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=a-khakimov_tinvest4s&metric=coverage)](https://sonarcloud.io/dashboard?id=a-khakimov_tinvest4s)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=a-khakimov_tinvest4s&metric=sqale_index)](https://sonarcloud.io/dashboard?id=a-khakimov_tinvest4s)

# tinvest4s

Библиотека предназначена для взаимодействия с [ОpenAPI Тинькофф Инвестиций](https://tinkoffcreditsystems.github.io/invest-openapi/).

## Начало работы

Для работы с библиотекой потребуется изучить [документацию](https://tinkoffcreditsystems.github.io/invest-openapi/) на ОpenAPI и получить в [личном кабинете](https://www.tinkoff.ru/invest/) токен для авторизации.

## Подключение библиотеки к проекту

В данный момент возможен вариант подключения библиотеки в качестве внешнего проекта.

Например, в sbt указать ссылку на репозиторий. 

```scala
lazy val tinvest4s = ProjectRef(uri("https://github.com/a-khakimov/tinvest4s.git#branch"), "tinvest4s")
lazy val root = (project in file(".")).dependsOn(tinvest4s)
```

## Пример использования

Клиенты разделены на REST и Streaming. Примеры использования можно найти в директори `examples`.

### REST-клиент

```scala
  override def run(args: List[String]): F[ExitCode] = {
    for {
      _ <- resources().use {
        case httpClient => {
          implicit val tinvestApi: TInvestApi[F] = new TInvestApiHttp4s[F](httpClient, "token")
          for {
            portfolio <- tinvestApi.getPortfolio
            _ <- Sync[F].delay {
              println(portfolio)
            }
          } yield ()
        }
      }
    } yield ExitCode.Success
  }
```

### Streaming клиент

Для работы со Streaming-api потребуется создать обработчик событий.
 
```scala
class StreamingEvents[F[_]: Sync] extends TInvestWSHandler[F] {
  override def handle(response: TInvestWSResponse): F[Unit] = {
    response match {
      case CandleResponse(_, _, candle) => ???
      case OrderBookResponse(_, _, orderBook) => ???
      case InstrumentInfoResponse(_, _, instrumentInfo) => ???
    }
  }
}
```

При создании websocket-клиента необходимо передать токен для авторизации.

```scala
  def resources(): Resource[F, WSConnectionHighLevel[F]] = {
    for {
      wsClient <- JdkWSClient[F](HttpClient.newHttpClient())
        .connectHighLevel(
          TInvestWSAuthorization()
            .withToken("token")
        )
    } yield wsClient
  }
```

Далее выполнить подписку на свечи с некоторым значением `CandleResolution`.

```scala
  override def run(args: List[String]): F[ExitCode] = {
    for {
      _ <- resources().use {
        wsClient => {
          implicit val handler: TInvestWSHandler[F] = new StreamingEvents[F]()
          implicit val tinvestWSApi: TInvestWSApi[F] = new TInvestWSApiHttp4s[F](wsClient, handler)
          for {
            _ <- tinvestWSApi.subscribeCandle("BBG005HLSZ23", CandleResolution.`1min`)
          } yield ()
        }
      }
    } yield ExitCode.Success
  }
```

