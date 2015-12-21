/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model;

public class PropertyKeys {

    public final static String PROP_STREAM_DEF               = "Streams.Name";

    public final static String PROP_LOG_FOLDER               = "Log.Folder";
    public final static String PROP_INPUT_FOLDER             = "Input.Folder";
    public final static String PROP_OUTPUT_FOLDER            = "Output.Folder";
    public final static String PROP_INPUT_FILEPATTERN        = "Input.FilePattern";

    public final static String PROP_NRWORKERS_INPUT          = "NrWorkers.Input";
    public final static String PROP_NRWORKERS_OUTPUT_HIT     = "NrWorkers.OutputHit";
    public final static String PROP_NRWORKERS_OUTPUT_NOHIT   = "NrWorkers.OutputNoHit";
    public final static String PROP_NRWORKERS_CHECKER        = "NrWorkers.Checker";
    public final static String PROP_NRWORKERS_ERROR          = "NrWorkers.Error";
    public final static String PROP_NRWORKERS_PP_HIT         = "NrWorkers.PostHit";
    public final static String PROP_NRWORKERS_PP_NOHIT       = "NrWorkers.PostNoHit";

    public final static String PROP_STREAMMGR_IMPL           = "StreamManager.Implementation";

    public final static String PROP_INPUTREADER_IMPL         = "InputReader.Implementation";
    public final static String PROP_OUTPUTWRITER_IMPL        = "OutputWriter.Implementation";
    public final static String PROP_ERRORWRITER_IMPL         = "ErrorProcessor.Implementation";
    public final static String PROP_ANALYZER_IMPL            = "MessageChecker.Implementation";
    public final static String PROP_POSTPROCESSOR_HIT_IMPL   = "PostProcessorHit.Implementation";
    public final static String PROP_POSTPROCESSOR_NOHIT_IMPL = "PostProcessorNoHit.Implementation";

    // public final static String PROP_MSGPARSER_IMPL =
    // "Messageparser.Implementation";
    public final static String PROP_FILEPARSER_IMPL          = "FileParser.Implementation";
    public final static String PROP_QUEUECHECKER_IMPL        = "QueueStatusChecker.Implementation";

    public final static String PROP_QUEUE_NAME_INPUT         = "InputQueue";
    public final static String PROP_QUEUE_NAME_HIT           = "HitQueue";
    public final static String PROP_QUEUE_NAME_NOHIT         = "NoHitQueue";
    public final static String PROP_QUEUE_NAME_DEFECT        = "DefectQueue";
    public final static String PROP_QUEUE_NAME_PP_HIT        = "PostHitQueue";
    public final static String PROP_QUEUE_NAME_PP_NOHIT      = "PostNoHitQueue";

    public final static String PROP_QUEUE_NAME_FINAL_HIT     = "FinalHitQueue";
    public final static String PROP_QUEUE_NAME_FINAL_NOHIT   = "FinalNoHitQueue";
    public final static String PROP_QUEUE_NAME_BACKLOG       = "BacklogQueue";

    // Lists to load

    public final static String PROP_LIST_DEFS                = "watchlists";
    public final static String PROP_REFLIST_DEFS             = "reflists";
    public final static String PROP_VALLIST_DEFS             = "vallists";
    public final static String PROP_LIST_DEF                 = "watchlist";
    public final static String PROP_REFLIST_DEF              = "reflist";
    public final static String PROP_TXNOHITOPTILIST_DEF      = "optilist-nohit";
    public final static String PROP_TXHITOPTILIST_DEF        = "optilist-hit";
    public final static String PROP_NOWORDHITLIST_DEF        = "nowordhitlist";
    public final static String PROP_VALLIST_DEF              = "vallist";
    public final static String PROP_LISTHIST_DEF             = PROP_LIST_DEFS + ".hist.folder";
    public final static String PROP_USEPROXY_DEF             = PROP_LIST_DEFS + ".use.sysproxy";

    // public final static String PROP_FILENAME_LIST_EU =
    // "list.EUList.filename";
    // public final static String PROP_FILENAME_LIST_OFAC =
    // "list.OFACList.filename";
    // public final static String PROP_FILENAME_LIST_DJ =
    // "list.DJList.filename";
    public final static String PROP_LISTLOADER               = "loader";
}
