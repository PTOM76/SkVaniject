package ml.pkom.skvaniject.lang.expression;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class PlayerExpre extends SimpleExpression<Number> {
    private Expression<Objective> objectives;
    private Expression<String> player;
    private Scoreboard scoreboard;

    static {
        Skript.registerExpression(PlayerExpre.class, Number.class, ExpressionType.COMBINED, "value of %~objective% of %~string%");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        objectives = (Expression<Objective>) expr[0];
        player = (Expression<String>) expr[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Expression: PlayerExpre , Value: " + objectives.toString(event, debug);
    }

    @Override
    @Nullable
    protected Number[] get(Event event) {
        Objective objectives = this.objectives.getSingle(event);
        String player = this.player.getSingle(event);
        if (objectives != null) {
            Score score = objectives.getScore(player);
            return new Number[] { score.getScore() };
        }
        return null;
    }
    
    @Override
    public void change(Event event, Object[] delta, ChangeMode mode) {
        Objective objectives = this.objectives.getSingle(event);
        String player = this.player.getSingle(event);
        Score score = objectives.getScore(player);
        int scoreInt = objectives.getScore(player).getScore();
        int deltaInt = ((Number)delta[0]).intValue();
        if (objectives != null) {
            if (mode == ChangeMode.SET) {
                score.setScore(deltaInt);
            } else if (mode == ChangeMode.ADD) {
                score.setScore(scoreInt + deltaInt);
            } else if (mode == ChangeMode.REMOVE) {
                score.setScore(scoreInt - deltaInt);
            } else if (mode == ChangeMode.RESET) {
                score.setScore(0);
            } else if (mode == ChangeMode.DELETE) {
                scoreboard.resetScores(player);
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }
}