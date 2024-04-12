package neilson.cafe;

/**
 * Enum class for the available sandwich addOn options.
 *
 * @author Danny Onuorah
 */
public enum SandwichAddOn {
    LETTUCE(0.30),
    TOMATOES(0.30),
    ONIONS(0.30),
    CHEESE(1.00);

    public double price;

    SandwichAddOn(double price) {
        this.price = price;
    }

    /**
     * Return sandwich addOn string
     *
     * @return AddOn string
     */
    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
