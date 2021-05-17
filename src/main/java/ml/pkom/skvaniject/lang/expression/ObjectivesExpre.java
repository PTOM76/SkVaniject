package ml.pkom.skvaniject.lang.expression;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ObjectivesExpre extends SimpleExpression<Objective> {
    private Expression<String> name;

    static {
        Skript.registerExpression(
                ObjectivesExpre.class, Objective.class, ExpressionType.SIMPLE, "objective[s] [(named|with name[s])] %~strings%");
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
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Expression: ObjectivesExpre , Value: " + name.toString(event, debug);
    }

    @Override
    @Nullable
    protected Objective[] get(Event event) {
        String name = this.name.getSingle(event);
        if (name != null) {
            return new Objective[] { Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name) };
        }
        return null;
    }
}