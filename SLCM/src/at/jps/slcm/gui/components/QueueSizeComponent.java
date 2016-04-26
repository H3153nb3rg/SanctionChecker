package at.jps.slcm.gui.components;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.DonutRenderer;

import com.vaadin.ui.Component;

public class QueueSizeComponent {

    // public static JFreeChartWrapper createGraphicsComponent() {
    // final JFreeChart createchart = createchart(createDataset());
    // return new JFreeChartWrapper(createchart);
    // }
    //
    // private static CategoryDataset createDataset() {
    //
    // // row keys...
    // final String y2009 = "2009";
    // final String y2008 = "2008";
    // final String y2007 = "2007";
    //
    // // column keys...
    // final String under5 = "< 5";
    // final String between5_9 = "5-9";
    // final String between10_14 = "10-14";
    // final String between15_19 = "15-19";
    // final String between20_24 = "20-24";
    //
    // // create the dataset...
    // final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    //
    // dataset.addValue(21299656, y2009, under5);
    // dataset.addValue(20609634, y2009, between5_9);
    // dataset.addValue(19973564, y2009, between10_14);
    // dataset.addValue(21537837, y2009, between15_19);
    // dataset.addValue(21539559, y2009, between20_24);
    //
    // dataset.addValue(21005852, y2008, under5);
    // dataset.addValue(20065249, y2008, between5_9);
    // dataset.addValue(20054627, y2008, between10_14);
    // dataset.addValue(21514358, y2008, between15_19);
    // dataset.addValue(21058981, y2008, between20_24);
    //
    // dataset.addValue(20724125, y2007, under5);
    // dataset.addValue(19849628, y2007, between5_9);
    // dataset.addValue(20314309, y2007, between10_14);
    // dataset.addValue(21473690, y2007, between15_19);
    // dataset.addValue(21032396, y2007, between20_24);
    //
    // return dataset;
    //
    // }
    //
    // /**
    // * Creates a sample chart.
    // *
    // * @param dataset
    // * the dataset.
    // * @return The chart.
    // */
    // private static JFreeChart createchart(final CategoryDataset dataset) {
    //
    // // create the chart...
    // final JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo 1", // chart
    // // title
    // "Age group", // domain axis label
    // "Number of members", // range axis label
    // dataset, // data
    // PlotOrientation.VERTICAL, // orientation
    // true, // include legend
    // true, // tooltips?
    // false // URLs?
    // );
    //
    // // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    //
    // // set the background color for the chart...
    // chart.setBackgroundPaint(Color.white);
    //
    // // get a reference to the plot for further customisation...
    // final CategoryPlot plot = (CategoryPlot) chart.getPlot();
    // plot.setBackgroundPaint(Color.lightGray);
    // plot.setDomainGridlinePaint(Color.white);
    // plot.setDomainGridlinesVisible(true);
    // plot.setRangeGridlinePaint(Color.white);
    //
    // // ******************************************************************
    // // More than 150 demo applications are included with the JFreeChart
    // // Developer Guide...for more information, see:
    // //
    // // > http://www.object-refinery.com/jfreechart/guide.html
    // //
    // // ******************************************************************
    //
    // // set the range axis to display integers only...
    // final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    // rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    //
    // // disable bar outlines...
    // final BarRenderer renderer = (BarRenderer) plot.getRenderer();
    // // renderer.setDrawBarOutline(false);
    //
    // // set up gradient paints for series...
    // final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, new Color(0, 0, 64));
    // final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0));
    // final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(64, 0, 0));
    // renderer.setSeriesPaint(0, gp0);
    // renderer.setSeriesPaint(1, gp1);
    // renderer.setSeriesPaint(2, gp2);
    //
    // final CategoryAxis domainAxis = plot.getDomainAxis();
    // domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
    // // OPTIONAL CUSTOMISATION COMPLETED.
    //
    // return chart;
    //
    // }
    //
    // private static Component getLevelChart() {
    //
    // final DefaultTableXYDataset ds = new DefaultTableXYDataset();
    // final NumberAxis y = new NumberAxis("Sales in thousands of $");
    //
    // XYSeries series;
    // final Calendar cal = Calendar.getInstance();
    // cal.set(Calendar.YEAR, 2006);
    // final long y2007 = cal.getTimeInMillis();
    // cal.set(Calendar.YEAR, 2007);
    // final long y2008 = cal.getTimeInMillis();
    // cal.set(Calendar.YEAR, 2008);
    // final long y2009 = cal.getTimeInMillis();
    //
    // series = new XYSeries("Asia", false, false);
    // series.add(y2007, 130942);
    // series.add(y2008, 78730);
    // series.add(y2009, 86895);
    // ds.addSeries(series);
    //
    // series = new XYSeries("Europe", false, false);
    // series.add(y2007, 207740);
    // series.add(y2008, 152144);
    // series.add(y2009, 130942);
    // ds.addSeries(series);
    //
    // series = new XYSeries("USA", false, false);
    // series.add(y2007, 217047);
    // series.add(y2008, 129870);
    // series.add(y2009, 174850);
    // ds.addSeries(series);
    //
    // // Paint p = new Color(0, 0, 0, Color.OPAQUE);
    // // r.setSeriesPaint(0, p);
    // // BasicStroke s = new BasicStroke(2);
    // // r.setSeriesStroke(0, s);
    //
    // final DateAxis x = new DateAxis("Year");
    // x.setDateFormatOverride(new SimpleDateFormat("yyyy"));
    // x.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 1));
    //
    // final XYPlot plot2 = new XYPlot(ds, x, y, new XYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES));
    // plot2.setForegroundAlpha(0.5f);
    //
    // final JFreeChart c = new JFreeChart(plot2);
    //
    // return new JFreeChartWrapper(c);
    // }

    // private static Component regressionChart() {
    //
    // final DefaultTableXYDataset ds = new DefaultTableXYDataset();
    //
    // XYSeries series;
    //
    // series = new XYSeries("Measured difference", false, false);
    // series.add(1, 1);
    // series.add(2, 4);
    // series.add(3, 6);
    // series.add(4, 9);
    // series.add(5, 9);
    // series.add(6, 11);
    // ds.addSeries(series);
    //
    // final JFreeChart scatterPlot = ChartFactory.createScatterPlot("Regression", "cm", "Measuring checkpoint", ds, PlotOrientation.HORIZONTAL, true, false, false);
    //
    // final XYPlot plot = (XYPlot) scatterPlot.getPlot();
    //
    // final double[] regression = Regression.getOLSRegression(ds, 0);
    //
    // // regression line points
    //
    // final double v1 = regression[0] + (regression[1] * 1);
    // final double v2 = regression[0] + (regression[1] * 6);
    //
    // final DefaultXYDataset ds2 = new DefaultXYDataset();
    // ds2.addSeries("regline", new double[][] { new double[] { 1, 6 }, new double[] { v1, v2 } });
    // plot.setDataset(1, ds2);
    // plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
    //
    // final JFreeChart c = new JFreeChart(plot);
    //
    // return new JFreeChartWrapper(c);
    // }

    public static Component getChart(final int input, final int hit, final int nohit) {

        final DataSeries dataSeries = new DataSeries();
        dataSeries.newSeries().add("input", input).add("hit", hit);
        dataSeries.newSeries().add("processed", hit + nohit).add("no hit", nohit);

        final SeriesDefaults seriesDefaults = new SeriesDefaults().setRenderer(SeriesRenderers.DONUT).setRendererOptions(new DonutRenderer().setSliceMargin(3).setStartAngle(-90));

        final Highlighter highlighter = new Highlighter().setShow(true).setShowTooltip(true).setTooltipAlwaysVisible(true).setKeepTooltipInsideChart(true);

        final Options options = new Options().setCaptureRightClick(true).setSeriesDefaults(seriesDefaults).setHighlighter(highlighter)
                .setSeriesColors("#3EA140", "#783F16", "#333333", "#999999", "#3EA140", "#3EA140", "#783F16", "#783F16", "#333333").setAnimate(true);

        final DCharts chart = new DCharts();

        // chart.setEnableChartDataMouseEnterEvent(true);
        // chart.setEnableChartDataMouseLeaveEvent(true);
        // chart.setEnableChartDataClickEvent(true);
        // chart.setEnableChartDataRightClickEvent(true);

        // chart.addHandler(new ChartDataMouseEnterHandler() {
        // @Override
        // public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
        // showNotification("CHART DATA MOUSE ENTER", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataMouseLeaveHandler() {
        // @Override
        // public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
        // showNotification("CHART DATA MOUSE LEAVE", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataClickHandler() {
        // @Override
        // public void onChartDataClick(ChartDataClickEvent event) {
        // showNotification("CHART DATA CLICK", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataRightClickHandler() {
        // @Override
        // public void onChartDataRightClick(ChartDataRightClickEvent event) {
        // showNotification("CHART DATA RIGHT CLICK", event.getChartData());
        // }
        // });

        chart.setDataSeries(dataSeries).setOptions(options).show();

        return chart;
    }

}
