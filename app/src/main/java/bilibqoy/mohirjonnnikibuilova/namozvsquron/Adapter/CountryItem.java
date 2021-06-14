package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

public class CountryItem {
    private int mCountryName;
    private int mFlagImage;

    public CountryItem(int countryName, int flagImage) {
        mCountryName = countryName;
        mFlagImage = flagImage;
    }

    public int getCountryName() {
        return mCountryName;
    }

    public int getFlagImage() {
        return mFlagImage;
    }
}