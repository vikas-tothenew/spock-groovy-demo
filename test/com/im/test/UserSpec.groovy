package com.im.test

import spock.lang.Specification

class UserSpec extends Specification {

    def "First test"() {
         expect:
         true
    }

    def "A Valid Password must have length greater than 7 and should not be empty or null"(){
        setup:
        User user = new User(password: pwd)
        when:
        boolean result = user.isValidPassword(pwd)
        then:
        result == expectedOutput
        where:
        pwd | expectedOutput
        "pwd123" | false
        "pwd12345" | true
        "" | false
        null | false
    }

    def "Testing of resend password and sending email for resetting password"() {
        setup:
        User user = Spy(User)
        user.encyryptPassword(_ as String) >> "New Dummy Password"
        def emailService = Mock(EmailService)
        user.emailService = emailService

        when:
        user.resetPasswordAndSendEmail()
        then:
        user.password == "New Dummy Password"

    }

    def "Testing of Password Encryption"(){
        setup:
        User user = new User()
        PasswordEncrypterService pes = Mock(PasswordEncrypterService)
        pes.encrypt(_ as String) >> "Test Encrypted Password"
        user.setPasswordEncrypterService(pes)

        when:
        String pass = user.encyryptPassword("userpassword1")
        then:
        pass == "Test Encrypted Password"
    }

    def "Testing Get Income Group"(){
        setup:
        User user = new User(incomePerMonth:income)
        when:
        String group = user.getIncomeGroup()
        then:
        group == expectedGroup
        where:
        income|expectedGroup
        4999|"MiddleClass"
        5000|"MiddleClass"
        5001|"Higher MiddleClass"
        9999|"Higher MiddleClass"
        10000|"Higher MiddleClass"
        10001|"Very Higher MiddleClass"
    }

    def "Testing Product Purchase"(){
        setup:
        User user = new User()
        Product product = new Product()
        product.name = "Refrigerator"
        when:
        user.purchase(product)
        then:
        user.purchasedProducts.size() == 1
        and:
        user.purchasedProducts.get(0).name == "Refrigerator"
    }

    def "Testing Product Cancelation"(){
        setup:
        Product product = new Product()
        product.name = "Refrigerator"
        User user = new User(purchasedProducts: [product])
        when:
        user.cancelPurchase(product)
        then:
        user.purchasedProducts.size() == 0
    }


    def "Testing categories sorting by interest"(){
        setup:
        User user = Spy(User)
        user.getInterestedInCategories() >> ["X","Y","Z","A","L","M"]
        when:
        List<String> sortedCategoriesActual = user.getSortedInterestedInCategories()
        then:
        sortedCategoriesActual == ["A","L","M","X","Y","Z"]
    }

}
