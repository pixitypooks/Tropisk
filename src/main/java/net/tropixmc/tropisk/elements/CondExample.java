package net.tropixmc.tropisk.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Has Permission")
@Description("Checks if a player has a specific permission")
@Examples({
    "if player has permission "myaddon.use":",
    "    send "You have permission!" to player"
})
@Since("1.0.0")
public class CondExample extends Condition {
    static {
        Skript.registerCondition(CondExample.class,
            "%players% (has|have) permission %string%",
            "%players% (doesn't|does not|do not|don't) have permission %string%"
        );
    }

    private Expression<Player> players;
    private Expression<String> permission;
    private boolean isNegated;

    @Override
    public boolean check(@NotNull Event e) {
        String perm = permission.getSingle(e);
        if (perm == null) return false;

        boolean hasPermission = true;
        for (Player player : players.getArray(e)) {
            if (!player.hasPermission(perm)) {
                hasPermission = false;
                break;
            }
        }

        return isNegated != hasPermission;
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return players.toString(e, debug) + (isNegated ? " doesn't have" : " has") + " permission " + permission.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        players = (Expression<Player>) exprs[0];
        permission = (Expression<String>) exprs[1];
        isNegated = matchedPattern == 1;
        return true;
    }
}