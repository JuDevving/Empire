package eu.judevving.empire.earth.storage;

import eu.judevving.empire.clock.Clock;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;

public enum ClaimResult {

    ALREADY_CLAIMED(Text.CLAIM_RESULT_ALREADY_CLAIMED, false),
    ALREADY_CLAIMED_SELF(Text.CLAIM_RESULT_ALREADY_CLAIMED_SELF, false),
    CAPITAL_ALREADY_CLAIMED(Text.CLAIM_RESULT_CAPITAL_ALREADY_CLAIMED, false),
    DIFFERENT_STATE_PLAYER_PRESENT(Text.CLAIM_RESULT_DIFFERENT_STATE_PLAYER_NEARBY, false),
    NO_CAPITAL(Text.CLAIM_RESULT_NO_CAPITAL, true),
    NOT_ENOUGH_POWER(Text.CLAIM_RESULT_NOT_ENOUGH_POWER, true),
    OUT_OF_BOUNDS(Text.CLAIM_RESULT_OUT_OF_BOUNDS, true),
    SUCCESS(Text.CLAIM_RESULT_SUCCESS, false, true),
    SUCCESS_CAPITAL(Text.CLAIM_RESULT_SUCCESS_CAPITAL, false, true),
    SUCCESS_RECENT(Text.CLAIM_RESULT_SUCCESS_RECENT, false, true),
    SUCCESS_TAKEOVER(Text.CLAIM_RESULT_SUCCESS_TAKEOVER, false, true),
    TOO_CLOSE(Text.CLAIM_RESULT_TOO_CLOSE, false),
    TOO_FAR(Text.CLAIM_RESULT_TOO_FAR, false),
    TOO_MANY_EDGES(Text.CLAIM_RESULT_TOO_MANY_EDGES, false),
    UNCLAIMABLE(Text.CLAIM_RESULT_UNCLAIMABLE, false),
    WRONG_WORLD(Text.CLAIM_RESULT_WRONG_WORLD, true),

    NOT_CLAIMED(Text.CLAIM_RESULT_NOT_CLAIMED, false),
    SUCCESS_UNCLAIM(Text.CLAIM_RESULT_SUCCESS_UNCLAIM, false, true),
    SUCCESS_UNCLAIM_TAKEOVER(Text.CLAIM_RESULT_SUCCESS_UNCLAIM_TAKEOVER, false, true),
    CANNOT_UNCLAIM_CAPITAL(Text.CLAIM_RESULT_CANNOT_UNCLAIM_CAPITAL, false),
    TOO_FAR_TAKEOVER(Text.CLAIM_RESULT_TOO_FAR_TAKEOVER, false);

    private final Text text;
    private final boolean success, cancelsAuto;

    ClaimResult(Text text, boolean cancelsAuto, boolean success) {
        this.text = text;
        this.cancelsAuto = cancelsAuto;
        this.success = success;
    }

    ClaimResult(Text text, boolean cancelsAuto) {
        this(text, cancelsAuto, false);
    }

    public static String getMessage(Human h, Square square, State owner, ClaimResult claimResult) {
        String arg2 = "";
        String arg3 = "";
        if (claimResult == ClaimResult.ALREADY_CLAIMED)
            arg2 = owner.getColoredNameAndId();
        if (claimResult == ClaimResult.NOT_ENOUGH_POWER)
            arg2 = h.getState().getPower() + GlobalFinals.STRING_OF_SHORT + (h.getState().getPower() + h.getState().getOneSquarePowerCost());
        if (claimResult == ClaimResult.SUCCESS_RECENT)
            arg2 = Clock.millisToString(Main.getPlugin().getEarth().getClaimTimeManager().getSecuredTime(square));
        if (claimResult == ClaimResult.SUCCESS_TAKEOVER)
            arg2 = owner.getColoredNameAndId();
        if (claimResult == ClaimResult.SUCCESS_UNCLAIM_TAKEOVER)
            arg2 = owner.getColoredNameAndId();
        if (claimResult == ClaimResult.TOO_CLOSE) {
            arg2 = (int) square.getBlockDistance(owner.getCapital()) + GlobalFinals.STRING_OF_SHORT + GlobalFinals.TAKEOVER_PROTECTION_RADIUS;
            arg3 = owner.getColoredNameAndId();
        }
        if (claimResult == ClaimResult.TOO_FAR)
            arg2 = (int) (square.getBlockDistance(h.getState().getCapital()) + 1) + GlobalFinals.STRING_OF_SHORT + h.getState().getClaimRadius();
        if (claimResult == ClaimResult.TOO_FAR_TAKEOVER)
            arg2 = (int) (square.getBlockDistance(h.getState().getCapital()) + 1) + GlobalFinals.STRING_OF_SHORT + h.getState().getClaimRadius();
        if (claimResult == ClaimResult.TOO_MANY_EDGES)
            arg2 = owner.countEdges(square) + GlobalFinals.STRING_OF_SHORT + GlobalFinals.TAKEOVER_MAX_EDGES;
        return claimResult.getText().get(h, square.getCenterBlockString(), arg2, arg3);
    }

    public boolean cancelsAuto() {
        return cancelsAuto;
    }

    public boolean isSuccess() {
        return success;
    }

    public Text getText() {
        return text;
    }
}
