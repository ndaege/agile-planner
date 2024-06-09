package com.planner.ui;

import com.planner.scripter.ScriptFSM;
import com.planner.ui.tables.TableFormatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SessionState implements TUIState {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void setupAndDisplayPage() {
        File file = new File("data/scripts");
        File[] files = file.listFiles();
        List<File> scriptList = new ArrayList<>();
        assert files != null;
        for (File f : files) {
            if (f.getName().length() > 5 && ".smpl".equals(f.getName().substring(f.getName().length() - 5))) {
                scriptList.add(f);
            }
        }
        System.out.println();
        System.out.println(TableFormatter.formatScriptOptionsTable(scriptList));
        System.out.print("\n                                                         Enter ID: ");
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (!input.isBlank() && hasInteger(input)) {
                int id = Integer.parseInt(input);
                if (id >= 0 && id < scriptList.size()) {
                    try {
                        TUIState.clearScreen();
                        ScriptFSM scriptFSM = new ScriptFSM();
                        scriptFSM.executeScript(scriptList.get(id).getAbsolutePath());
                        // need to have ScheduleManager reset (causing a lot of glitches when you keep rerunning the same file over and over)
                        scriptFSM.getScheduleManager().resetData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.print("\n\nWould you like to leave? ");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private boolean hasInteger(String s) {
        try {
            Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
