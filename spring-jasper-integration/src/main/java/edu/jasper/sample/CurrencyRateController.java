package edu.jasper.sample;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class CurrencyRateController {

    @GetMapping("/")
    public String mainView() {
        return "main";
    }

    @GetMapping("/exchangeRates")
    public String handleForexRequest(@RequestParam("renderAs") String renderAs, Model model) {
        model.addAttribute("todayCurrencyRates", getTodayForexRates());
        if(renderAs != null && renderAs.equalsIgnoreCase("pdf")){
        	return "excahngeRatesViewPdf";
        }
        return "exchangeRatesViewHtml";
    }
    
    // Creating sample exchange rates data
    private List<CurrencyRate> getTodayForexRates() {
        //dummy rates
        List<CurrencyRate> currencyRates = new ArrayList<>();
        Date today = new Date();
        List<Currency> currencies = new ArrayList<>(Currency.getAvailableCurrencies());

        for (int i = 0; i < currencies.size(); i += 2) {
            String currencyPair = currencies.get(i) + "/" + currencies.get(i + 1);
            CurrencyRate cr = new CurrencyRate();
            cr.setCurrencyPair(currencyPair);
            cr.setDate(today);
            BigDecimal bidPrice = new BigDecimal(Math.random() * 5 + 1);
            bidPrice = bidPrice.setScale(3, RoundingMode.CEILING);
            cr.setBidPrice(bidPrice);
            BigDecimal askPrice = new BigDecimal(bidPrice.doubleValue() + Math.random() * 2 + 0.5);
            askPrice = askPrice.setScale(3, RoundingMode.CEILING);
            cr.setAskPrice(askPrice);

            currencyRates.add(cr);
        }
        return currencyRates;
    }
}