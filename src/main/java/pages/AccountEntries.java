package pages;

public enum AccountEntries {

    CREATE_ACCOUNT("Create Account"),
    SIGN_IN("Sign In");
    private String displayName;

    AccountEntries(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
