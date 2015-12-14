# gunstar
Java client for [stockfigher.io](https://stockfighter.io/) trading game

# Sample Usage
    GunstarContext context = new GunstarContext("my_api_key_here");
    TradingAPI api = new TradingAPI(context);
    StockSymbolList stockSymbolList = api.listStocks("TESTEX");

# License
Apache License Version 2.0, see LICENSE file.