package ml.pkom.skvaniject.lang.expression;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class DisplayNameExpre extends SimpleExpression<String> {
    private Expression<Objective> objectives;
    private String name;
    static {
        Skript.registerExpression(
                DisplayNameExpre.class, String.class, ExpressionType.COMBINED, "display name of %~objectives%");
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
        return "Expression: DisplayNameExpre , Value: " + objectives.toString(event, debug);
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        Objective objectives = this.objectives.getSingle(event);
        if (objectives != null) {
            name = objectives.getName();
            return new String[] { name };
        }
        return null;
    }
    
    @Override
    public void change(Event event, Object[] delta, ChangeMode mode) {
        Objective objectives = this.objectives.getSingle(event);
        if (objectives != null) {
            if (mode == ChangeMode.SET) {
                objectives.setDisplayName((String)delta[0]);
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }
}