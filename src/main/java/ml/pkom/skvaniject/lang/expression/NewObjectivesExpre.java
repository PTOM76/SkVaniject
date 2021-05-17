package ml.pkom.skvaniject.lang.expression;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class NewObjectivesExpre extends SimpleExpression<Objective> {
    private Expression<String> name;
    private Expression<String> displayName;
    private Expression<String> criteria;    

    static {
        Skript.registerExpression(
                NewObjectivesExpre.class, 
                Objective.class, ExpressionType.SIMPLE, "[skvaniject] new objective[s] [(named|with [the] name)] %-string% [(display( |-)named|with [the] display( |-)name) %-string%] [with [the] criteria %-string%]");
    }

    @Override
    public Class<? extends Objective> getReturnType() {
        return Objective.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        name = (Expression<String>) expr[0];
        displayName = (Expression<String>) expr[1];
        criteria = (Expression<String>) expr[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Expression: NewObjectivesExpre , Value: " + name.toString(event, debug);
    }

    @Override
    @Nullable
    protected Objective[] get(Event event) {
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
            return null;
        }
        return new Objective[] { objective };
    }
}