package net.tropixmc.tropisk.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Player Name")
@Description("Returns the name of a player")
@Examples({
    "set {addon_name} to player's name",
    "send "Your name is %player's name%" to player"
})
@Since("1.0.0")
public class ExprExample extends SimplePropertyExpression<Player, String> {
    static {
        register(ExprExample.class, String.class, "name", "players");
    }

    @Override
    public String convert(Player player) {
        return player.getName();
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "name";
    }
}