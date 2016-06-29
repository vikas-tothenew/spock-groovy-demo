package com.im.test

import spock.lang.IgnoreRest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class TransactionSpec extends Specification {

    def "Testing when balance is greater than product price"() {
        setup:
        Transaction trans = new Transaction();
        User user = new User(balance: balance, username: 'Lovjit');
        Product product = new Product(price: productPrice)
        when:
        trans.sell(product, user);
        then:
        user.purchasedProducts.size() == countOfPurchasedProducts;
        where:
        balance | productPrice | countOfPurchasedProducts
        100     | 45           | 1
        100     | 86           | 1
    }

    def "Testing when balance is less than product price.Exception is thrown"() {
        setup:
        Transaction trans = new Transaction();
        User user = new User(balance: 50, username: 'Lovjit');
        Product product = new Product(price: 100)
        when:
        trans.sell(product, user);
        then:
        thrown(SaleException);
    }

    def "Testing when user balance is credited when sale is cancelled"() {
        setup:
        def transaction = Spy(Transaction)
        User user = new User(balance: 50, username: 'Lovjit');
        Product product = new Product(price: 100)

        transaction.calculateDiscount(_ as Product, _ as User) >> new BigDecimal(30)

        when:
        transaction.cancelSale(product, user);
        then:
        user.balance == 120
    }

}
