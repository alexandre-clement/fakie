package com.fakie.cli.dataset;

import com.fakie.cli.FakieSubCommand;
import com.fakie.cli.learning.AprioriCommand;
import com.fakie.cli.learning.FPGrowthCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        commandListHeading = "@|bold %nLearning Algorithm|@:%n",
        subcommands = {FPGrowthCommand.class, AprioriCommand.class})
public abstract class GraphLoaderCommand extends FakieSubCommand {
    private static final Logger logger = LogManager.getFormatterLogger();

    @Override
    protected void process() {
        loadGraph();
    }

    protected abstract void loadGraph();
}
