package ml.pkom.skvaniject.lang.expression;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class CriteriaExpre extends SimpleExpression<String> {
    private Expression<Objective> objectives;
    private String name;
    static {
        Skript.registerExpression(
                CriteriaExpre.class, String.class, ExpressionType.SIMPLE, "criteria of %~objective%");
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        objectives = (Expression<Objective>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Expression: CriteriaExpre , Value: " + objectives.toString(event, debug);
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        Objective objectives = this.objectives.getSingle(event);
        if (objectives != null) {
            name = objectives.getCriteria();
            return new String[] { name };
        }
        return null;
    }
}