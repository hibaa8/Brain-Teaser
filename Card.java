import javax.swing.Icon;

public class Card implements Comparable<Card> {
    private Icon icon;
    // private String image;

    public Card(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public int compareTo(Card o) {
        return 0;
    }

}
