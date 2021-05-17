package ml.pkom.skvaniject.lang.effect;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class DeleteObjective extends Effect {

    static {
        Skript.registerEffect(DeleteObjective.class, "[skvaniject] delete %~objective%");
    }

    private Expression<Objective> objective;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expre, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        objective = (Expression<Objective>) expre[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "effect: DeleteObjective , Name: " + objective.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        try {
            Objective objective = this.objective.getSingle(event);
            objective.unregister(); 
        } catch (NullPointerException e) {

        }
    }
}
