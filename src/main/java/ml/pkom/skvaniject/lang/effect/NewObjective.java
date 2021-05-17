package ml.pkom.skvaniject.lang.effect;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class NewObjective extends Effect {
    private Expression<String> name;
    private Expression<String> displayName;
    private Expression<String> criteria;

    static {
        Skript.registerEffect(NewObjective.class, "[skvaniject] new objective[s] [(named|with [the] name)] %-string% [(display( |-)named|with [the] display( |-)name) %-string%] [with [the] criteria %-string%]");
    }

    private Expression<Objective> objective;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        name = (Expression<String>) expr[0];
        displayName = (Expression<String>) expr[1];
        criteria = (Expression<String>) expr[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "effect: NewObjective , Name: " + objective.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        String name;
        String displayName;
        String criteria;
        try {
            name = this.name.getSingle(event);
        } catch (NullPointerException e) {
            name = null;
        }
        try {
            displayName = this.displayName.getSingle(event);
        } catch (NullPointerException e) {
            displayName = null;
        }
        try {
            criteria = this.criteria.getSingle(event);
        } catch (NullPointerException e) {
            criteria = null;
        }
        if (name == null) name = "<none>";
        if (criteria == null) criteria = "dummy";
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective objective;
        try {
            objective = scoreboard.registerNewObjective(name, criteria);
            if (displayName != null) objective.setDisplayName(displayName);
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Objective " + name + " already existed, so it was not created anew.");
        }
    }
}
