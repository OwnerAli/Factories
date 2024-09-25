package dev.viaduct.factories.domain.banks.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.resources.currencies.Currency;
import dev.viaduct.factories.scoreboards.impl.ResourceListing;

import java.util.ArrayList;
import java.util.List;

public class CurrencyBank extends Bank {

    public CurrencyBank() {
        super();
        FactoriesPlugin.getInstance()
                .getResourceManager()
                .getResourceSet()
                .stream().filter(resource -> resource instanceof Currency)
                .map(resource -> (Currency)resource)
                .forEach(resource -> resourceMap.put(resource, 0.0));
    }

    @Override
    public ResourceListing getScoreboardData() {
        String title = "&f&lCurrency    ";

        List<String> currencyList = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        for (Resource resource :  resourceMap.keySet()) {
            currencyList.add(resource.getFormattedName() + resourceMap.get(resource));
            teamNames.add(resource.getName());
        }
        return new ResourceListing(title, currencyList, teamNames);
    }
}
