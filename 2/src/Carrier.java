/**
 * Представляє оператора мобільного зв'язку з тарифами та можливостями мережі
 */
class Carrier {
    private String name;
    private String code;
    private double privateNetworkRate;
    private double publicNetworkRate;

    /**
     * Створює нового оператора із зазначеними тарифами
     * @param name назва оператора для відображення
     * @param code унікальний ідентифікатор оператора
     * @param privateNetworkRate вартість за секунду для дзвінків у межах одного оператора
     * @param publicNetworkRate вартість за секунду для дзвінків між операторами
     */
    public Carrier(String name, String code, double privateNetworkRate,
                   double publicNetworkRate) {
        this.name = name;
        this.code = code;
        this.privateNetworkRate = privateNetworkRate;
        this.publicNetworkRate = publicNetworkRate;
    }

    /**
     * Перевіряє, чи збігається цей оператор з іншим
     * @param otherCarrier оператор для порівняння
     * @return true, якщо оператори однакові
     */
    public boolean isSameCarrier(Carrier otherCarrier) {
        if (otherCarrier == null) return false;
        if (otherCarrier.getName().equals(this.name)) {
            return true;
        }
       return false;
    }

    /**
     * Розраховує вартість дзвінка в межах одного оператора
     * @param durationInSeconds тривалість дзвінка
     * @return загальна вартість дзвінка
     */
    public double calculatePrivateNetworkCost(double durationInSeconds) {
        return durationInSeconds * privateNetworkRate;
    }

    /**
     * Розраховує вартість дзвінка між операторами
     * @param durationInSeconds тривалість дзвінка
     * @return загальна вартість дзвінка
     */
    public double calculatePublicNetworkCost(double durationInSeconds) {
        return durationInSeconds * publicNetworkRate;
    }

    /**
     * Повертає відформатовану інформацію про оператора
     * @return назва та код оператора
     */
    public String getCarrierInfo() {
        return name + " (" + code + ")";
    }

    public String getName() { return name; }
    public double getPrivateNetworkRate() { return privateNetworkRate; }
    public double getPublicNetworkRate() { return publicNetworkRate; }


}