package net.tropixmc.tropisk.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Send Message")
@Description("Sends a message to a player")
@Examples({
    "send "Hello!" to player",
    "send "Welcome %player%!" to all players"
})
@Since("1.0.0")
public class EffExample extends Effect {
    static {
        Skript.registerEffect(EffExample.class,
            "send %string% to %players%"
        );
    }

    private Expression<String> message;
    private Expression<Player> players;

    @Override
    protected void execute(@NotNull Event e) {
        String msg = message.getSingle(e);
        if (msg == null) return;

        for (Player player : players.getArray(e)) {
            player.sendMessage(msg);
        }
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "send " + message.toString(e, debug) + " to " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        message = (Expression<String>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }
}