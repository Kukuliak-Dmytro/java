/**
 * Представляє мобільний телефон з можливостями дзвінків та управлінням рахунком
 */
class Phone {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private double balance;
    private double privateNetworkSecondsBalance;
    private double publicNetworkSecondsBalance;
    private Carrier carrier;

    /**
     * Створює новий телефон з повною інформацією про рахунок
     *
     * @param id                           унікальний ідентифікатор телефону
     * @param firstName                    ім'я власника
     * @param lastName                     прізвище власника
     * @param middleName                   по батькові власника
     * @param phoneNumber                  номер телефону
     * @param balance                      грошовий баланс
     * @param privateNetworkSecondsBalance безкоштовні секунди для дзвінків у межах одного оператора
     * @param publicNetworkSecondsBalance  безкоштовні секунди для дзвінків між операторами
     * @param carrier                      операторська мережа телефону
     */
    public Phone(Integer id, String firstName, String lastName, String middleName,
                 String phoneNumber, double balance, double privateNetworkSecondsBalance,
                 double publicNetworkSecondsBalance, Carrier carrier) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.privateNetworkSecondsBalance = privateNetworkSecondsBalance;
        this.publicNetworkSecondsBalance = publicNetworkSecondsBalance;
        this.carrier = carrier;
    }

    /**
     * Ініціює дзвінок на інший телефон, автоматично вибираючи тип мережі
     *
     * @param targetPhone       телефон, на який здійснюється дзвінок
     * @param durationInSeconds тривалість дзвінка
     * @return true, якщо дзвінок був успішним
     */
    public boolean initiateCall(Phone targetPhone, double durationInSeconds) {
        if (targetPhone == null) {
            System.out.println("Неможливо зателефонувати: цільовий телефон не існує");
            return false;
        }

        boolean sameCarrier = this.carrier.isSameCarrier(targetPhone.carrier);

        if (sameCarrier) {
            return makePrivateNetworkCall(targetPhone, durationInSeconds);
        } else {
            return makePublicNetworkCall(targetPhone, durationInSeconds);
        }
    }

    /**
     * Обробляє дзвінок, використовуючи приватну мережу для дзвінків у межах одного оператора
     *
     * @param targetPhone       телефон, на який телефонують
     * @param durationInSeconds тривалість дзвінка
     * @return true, якщо дзвінок завершився успішно
     */
    private boolean makePrivateNetworkCall(Phone targetPhone, double durationInSeconds) {
        double cost = 0.0;

        if (this.privateNetworkSecondsBalance >= durationInSeconds) {
            this.privateNetworkSecondsBalance -= durationInSeconds;
            System.out.printf("Дзвінок у приватній мережі від %s до %s (%s) протягом %.1f секунд\n",
                    this.carrier.getName(), targetPhone.carrier.getName(),
                    targetPhone.getFullName(), durationInSeconds);
            System.out.printf("Використано доступні секунди | Залишок на балансі: %.2f грн | Залишок приватних секунд: %.1f\n",
                    this.balance, this.privateNetworkSecondsBalance);
        } else {
            double secondsFromBalance = this.privateNetworkSecondsBalance;
            double secondsNeedingPayment = durationInSeconds - secondsFromBalance;

            cost = this.carrier.calculatePrivateNetworkCost(secondsNeedingPayment);

            if (this.balance < cost) {
                System.out.printf("Недостатньо коштів. Потрібно %.2f грн для %.1f секунд, але на рахунку %.2f грн\n",
                        cost, secondsNeedingPayment, this.balance);
                return false;
            }

            this.privateNetworkSecondsBalance = 0;
            this.balance -= cost;

            System.out.printf("Дзвінок у приватній мережі від %s до %s (%s) протягом %.1f секунд\n",
                    this.carrier.getName(), targetPhone.carrier.getName(),
                    targetPhone.getFullName(), durationInSeconds);
            System.out.printf("Використано %.1f безкоштовних секунд + сплачено %.2f грн за %.1f секунд | Залишок на балансі: %.2f грн\n",
                    secondsFromBalance, cost, secondsNeedingPayment, this.balance);
        }

        return true;
    }

    /**
     * Обробляє дзвінок, використовуючи публічну мережу для дзвінків між операторами
     *
     * @param targetPhone       телефон, на який телефонують
     * @param durationInSeconds тривалість дзвінка
     * @return true, якщо дзвінок завершився успішно
     */
    private boolean makePublicNetworkCall(Phone targetPhone, double durationInSeconds) {
        double cost = 0.0;

        if (this.publicNetworkSecondsBalance >= durationInSeconds) {
            this.publicNetworkSecondsBalance -= durationInSeconds;
            System.out.printf("Дзвінок у публічній мережі від %s до %s (%s) протягом %.1f секунд\n",
                    this.carrier.getName(), targetPhone.carrier.getName(),
                    targetPhone.getFullName(), durationInSeconds);
            System.out.printf("Використано безкоштовні секунди | Залишок на балансі: %.2f грн | Залишок публічних секунд: %.1f\n",
                    this.balance, this.publicNetworkSecondsBalance);
        } else {
            double secondsFromBalance = this.publicNetworkSecondsBalance;
            double secondsNeedingPayment = durationInSeconds - secondsFromBalance;

            cost = this.carrier.calculatePublicNetworkCost(secondsNeedingPayment);

            if (this.balance < cost) {
                System.out.printf("Недостатньо коштів. Потрібно %.2f грн для %.1f секунд, але на рахунку %.2f грн\n",
                        cost, secondsNeedingPayment, this.balance);
                return false;
            }

            this.publicNetworkSecondsBalance = 0;
            this.balance -= cost;

            System.out.printf("Дзвінок у публічній мережі від %s до %s (%s) протягом %.1f секунд\n",
                    this.carrier.getName(), targetPhone.carrier.getName(),
                    targetPhone.getFullName(), durationInSeconds);
            System.out.printf("Використано %.1f безкоштовних секунд + сплачено %.2f грн за %.1f секунд | Залишок на балансі: %.2f грн\n",
                    secondsFromBalance, cost, secondsNeedingPayment, this.balance);
        }

        return true;
    }

    /**
     * Додає гроші на рахунок телефону
     *
     * @param amount сума для додавання
     */
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("Додано %.2f грн на баланс. Новий баланс: %.2f грн\n", amount, this.balance);
        }
    }

    /**
     * Додає безкоштовні секунди для дзвінків у межах одного оператора
     *
     * @param seconds секунди для додавання
     */
    public void addPrivateNetworkSeconds(double seconds) {
        if (seconds > 0) {
            this.privateNetworkSecondsBalance += seconds;
            System.out.printf("Додано %.1f секунд приватної мережі. Новий баланс: %.1f секунд\n",
                    seconds, this.privateNetworkSecondsBalance);
        }
    }

    /**
     * Додає безкоштовні секунди для дзвінків між операторами
     *
     * @param seconds секунди для додавання
     */
    public void addPublicNetworkSeconds(double seconds) {
        if (seconds > 0) {
            this.publicNetworkSecondsBalance += seconds;
            System.out.printf("Додано %.1f секунд публічної мережі. Новий баланс: %.1f секунд\n",
                    seconds, this.publicNetworkSecondsBalance);
        }
    }

    /**
     * Змінює операторську мережу телефону
     *
     * @param newCarrier новий оператор для переключення
     */
    public void switchCarrier(Carrier newCarrier) {
        if (newCarrier != null) {
            System.out.printf("Переключення з %s на %s\n", this.carrier.getName(), newCarrier.getName());
            this.carrier = newCarrier;
        }
    }

    /**
     * Повертає повне ім'я власника з належним форматуванням
     *
     * @return відформатоване повне ім'я
     */
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName);

        if (middleName != null && !middleName.trim().isEmpty()) {
            fullName.append(" ").append(middleName);
        }

        fullName.append(" ").append(lastName);
        return fullName.toString();
    }

    /**
     * Відображає повну інформацію про стан рахунку
     */
    public void displayAccountStatus() {
        System.out.println("=== Стан Рахунку ===");
        System.out.println("Власник: " + getFullName());
        System.out.println("Номер телефону: " + phoneNumber);
        System.out.println("Оператор: " + carrier.getCarrierInfo());
        System.out.printf("Баланс: %.2f грн\n", balance);
        System.out.printf("Секунди приватної мережі: %.1f\n", privateNetworkSecondsBalance);
        System.out.printf("Секунди публічної мережі: %.1f\n", publicNetworkSecondsBalance);
        System.out.printf("Тариф приватної мережі: %.3f грн/секунду\n", carrier.getPrivateNetworkRate());
        System.out.printf("Тариф публічної мережі: %.3f грн/секунду\n", carrier.getPublicNetworkRate());
        System.out.println("====================");
    }

    /**
     * Перевіряє, чи можна здійснити дзвінок зазначеної тривалості
     *
     * @param targetPhone       телефон, на який здійснюється дзвінок
     * @param durationInSeconds бажана тривалість дзвінка
     * @return true, якщо дзвінок можливий з поточним балансом
     */
    public boolean canMakeCall(Phone targetPhone, double durationInSeconds) {
        boolean sameCarrier = this.carrier.isSameCarrier(targetPhone.carrier);

        if (sameCarrier) {
            if (this.privateNetworkSecondsBalance >= durationInSeconds) {
                return true;
            }
            double secondsNeedingPayment = durationInSeconds - this.privateNetworkSecondsBalance;
            double cost = this.carrier.calculatePrivateNetworkCost(secondsNeedingPayment);
            return this.balance >= cost;
        } else {
            if (this.publicNetworkSecondsBalance >= durationInSeconds) {
                return true;
            }
            double secondsNeedingPayment = durationInSeconds - this.publicNetworkSecondsBalance;
            double cost = this.carrier.calculatePublicNetworkCost(secondsNeedingPayment);
            return this.balance >= cost;
        }
    }
}