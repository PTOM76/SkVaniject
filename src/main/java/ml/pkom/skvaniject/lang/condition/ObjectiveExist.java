package ml.pkom.skvaniject.lang.condition;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.eclipse.jdt.annotation.Nullable;
 
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
 
public class ObjectiveExist extends Condition {
    private Expression<String> name;
    private Scoreboard scoreboard;

    static {
        Skript.registerCondition(ObjectiveExist.class, "objective[s] [(named|with [the] name)] %-string% (1¦(is|=|do|does)|2¦(is|do|does)(n't|[ ]not)) (exist|set)");
    }
 
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        name = (Expression<String>) expr[0];
        setNegated(parser.mark == 1);
        return true;
    }
 
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "name:" + name.toString(event, debug);
    }
 
    @Override
    public boolean check(Event event) {
        String name = this.name.getSingle(event);
        if (name == null) return isNegated();
        try {
            scoreboard.getObjective(name);
        } catch (IllegalArgumentException e) {
            return isNegated();
        }
        return !isNegated();
    }
 
}