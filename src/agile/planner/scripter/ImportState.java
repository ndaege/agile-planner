package agile.planner.scripter;

import agile.planner.scripter.exception.InvalidPreProcessorException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class ImportState extends State {

    @Override
    protected void processFunc(String line) {
        if(!importPP) {
            throw new InvalidPreProcessorException("No imports are allowed due to the absence of the __IMPORT__ flag");
        }
        String[] token = processArguments(line, 1, null);
        scheduleManager.processJBinFile("data/" + token[0]);
        importPP = false;
//        LocalDate ld = LocalDate.parse("");
//        ld.get
    }
}
