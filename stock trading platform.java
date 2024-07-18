import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Stock class
class Stock {
    private String name;
    private String ticker;
    private double price;

    public Stock(String name, String ticker, double price) {
        this.name = name;
        this.ticker = ticker;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

// Market class
class Market {
    private ArrayList<Stock> stocks;

    public Market() {
        stocks = new ArrayList<>();
        stocks.add(new Stock("Apple", "AAPL", 150.0));
        stocks.add(new Stock("Google", "GOOGL", 2800.0));
        stocks.add(new Stock("Amazon", "AMZN", 3400.0));
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public Stock getStockByTicker(String ticker) {
        for (Stock stock : stocks) {
            if (stock.getTicker().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }

    public void simulateMarketChanges() {
        for (Stock stock : stocks) {
            double newPrice = stock.getPrice() * (0.95 + (Math.random() * 0.1)); 
            stock.setPrice(newPrice);
        }
    }
}

// Portfolio class
class Portfolio {
    private Map<Stock, Integer> holdings;
    private double cash;

    public Portfolio(double initialCash) {
        holdings = new HashMap<>();
        this.cash = initialCash;
    }

    public double getCash() {
        return cash;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > cash) {
            System.out.println("Not enough cash to complete the purchase.");
            return;
        }
        cash -= cost;
        holdings.put(stock, holdings.getOrDefault(stock, 0) + quantity);
    }

    public void sellStock(Stock stock, int quantity) {
        if (!holdings.containsKey(stock) || holdings.get(stock) < quantity) {
            System.out.println("Not enough stock to sell.");
            return;
        }
        cash += stock.getPrice() * quantity;
        holdings.put(stock, holdings.get(stock) - quantity);
        if (holdings.get(stock) == 0) {
            holdings.remove(stock);
        }
    }

    public void viewPortfolio() {
        System.out.println("Cash: $" + cash);
        System.out.println("Holdings:");
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            Stock stock = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(stock.getName() + " (" + stock.getTicker() + "): " + quantity + " shares at $" + stock.getPrice() + " each");
        }
    }
}

// TradingPlatform class
public class TradingPlatform {
    private static Market market = new Market();
    private static Portfolio portfolio = new Portfolio(10000.0); // Initial cash of $10,000

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Simulate Market Changes");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    portfolio.viewPortfolio();
                    break;
                case 5:
                    market.simulateMarketChanges();
                    System.out.println("Market data updated.");
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : market.getStocks()) {
            System.out.println(stock.getName() + " (" + stock.getTicker() + "): $" + stock.getPrice());
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter the ticker symbol of the stock to buy: ");
        String ticker = scanner.next();
        Stock stock = market.getStockByTicker(ticker);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter the quantity to buy: ");
        int quantity = scanner.nextInt();
        portfolio.buyStock(stock, quantity);
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("Enter the ticker symbol of the stock to sell: ");
        String ticker = scanner.next();
        Stock stock = market.getStockByTicker(ticker);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter the quantity to sell: ");
        int quantity = scanner.nextInt();
        portfolio.sellStock(stock, quantity);
    }
}
