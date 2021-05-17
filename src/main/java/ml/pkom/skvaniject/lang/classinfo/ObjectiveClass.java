package ml.pkom.skvaniject.lang.classinfo;

import org.bukkit.scoreboard.Objective;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class ObjectiveClass {

    static {
        Classes.registerClass(new ClassInfo<>(Objective.class, "objective").user("objectives?").name("Objective")
                //.description("")
                //.examples("")
                .defaultExpression(new EventValueExpression<>(Objective.class)).parser(new Parser<Objective>() {

                    @Override
                    @Nullable
                    public Objective parse(String input, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "name:[string]";
                    }

                    @Override
                    public String toString(Objective objective, int flags) {
                        return toVariableNameString(objective);
                    }

                    @Override
                    public String toVariableNameString(Objective objective) {
                        return "name:" + objective.toString();
                    }
                }
            )
        );
    }
}
