package ua.com.airport.utils;

import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class SplitPaneDividerSlider {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    private final Direction direction;
    private final SplitPane splitPane;
    private final int dividerIndex;
    private BooleanProperty aimContentVisibleProperty;
    private DoubleProperty lastDividerPositionProperty;
    private DoubleProperty currentDividerPositionProperty;
    private Region content;
    private double contentInitialMinWidth;
    private double contentInitialMinHeight;
    private Transition slideTransition;
    private Duration cycleDuration;
    private SplitPane.Divider dividerToMove;
    private AnchorPane leftFilters;

    public SplitPaneDividerSlider(SplitPane splitPane, int dividerIndex, Direction direction, AnchorPane leftFilters) {
        this(splitPane, dividerIndex, direction, Duration.millis(7000.0), leftFilters);//////mine
    }

    public SplitPaneDividerSlider(SplitPane splitPane,
                                  int dividerIndex,
                                  Direction direction,
                                  Duration cycleDuration,
                                  AnchorPane leftFilters) {//////mine
        this.direction = direction;
        this.splitPane = splitPane;
        this.dividerIndex = dividerIndex;
        this.cycleDuration = cycleDuration;
        this.leftFilters = leftFilters; //////mine
        init();
    }

    private void init() {
        slideTransition = new SlideTransition(cycleDuration);

        // figure out right splitpane content
        switch (direction) {
            case LEFT:
            case UP:
                content = (Region) splitPane.getItems().get(dividerIndex);
                break;
            case RIGHT:
            case DOWN:
                content = (Region) splitPane.getItems().get(dividerIndex + 1);
                break;
        }
        contentInitialMinHeight = content.getMinHeight();
        contentInitialMinWidth = content.getMinWidth();
        dividerToMove = splitPane.getDividers().get(dividerIndex);

        aimContentVisibleProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                // store divider position before transition:
                setLastDividerPosition(splitPane.getDividers().get(dividerIndex).getPosition());
                // "arm" current divider position before transition:
                setCurrentDividerPosition(getLastDividerPosition());
            }
            content.setMinSize(0.0, 0.0);
            slideTransition.play();
        });
    }

    private void restoreContentSize() {
        content.setMinHeight(contentInitialMinHeight);
        content.setMinWidth(contentInitialMinWidth);
        setCurrentDividerPosition(getLastDividerPosition());
        //System.out.println("inner restore "+getLastDividerPosition()+" "+contentInitialMinHeight+" "+contentInitialMinWidth);
    }

    public BooleanProperty aimContentVisibleProperty() {
        if (aimContentVisibleProperty == null) {
            aimContentVisibleProperty = new SimpleBooleanProperty(true);
        }
        return aimContentVisibleProperty;
    }

    public void setAimContentVisible(boolean aimContentVisible) {
        aimContentVisibleProperty().set(aimContentVisible);
    }

    public boolean isAimContentVisible() {
        return aimContentVisibleProperty().get();
    }

    public DoubleProperty lastDividerPositionProperty() {
        if (lastDividerPositionProperty == null) {
            lastDividerPositionProperty = new SimpleDoubleProperty();
        }
        return lastDividerPositionProperty;
    }

    public double getLastDividerPosition() {
        return lastDividerPositionProperty().get();
    }

    public void setLastDividerPosition(double lastDividerPosition) {
        lastDividerPositionProperty().set(lastDividerPosition);
    }

    public DoubleProperty currentDividerPositionProperty() {
        if (currentDividerPositionProperty == null) {
            currentDividerPositionProperty = new SimpleDoubleProperty();
        }
        return currentDividerPositionProperty;
    }

    public double getCurrentDividerPosition() {
        return currentDividerPositionProperty().get();
    }

    public void setCurrentDividerPosition(double currentDividerPosition) {
        currentDividerPositionProperty().set(currentDividerPosition);
        dividerToMove.setPosition(currentDividerPosition);
        // System.out.println("inner divider "+currentDividerPosition);
    }

    private class SlideTransition extends Transition {

        public SlideTransition(final Duration cycleDuration) {
            setCycleDuration(cycleDuration);
        }

        @Override
        protected void interpolate(double d) {
            switch (direction) {
                case LEFT:
                case UP:
                    // intent to slide in content:
                    if (isAimContentVisible()) {
                        if ((getCurrentDividerPosition() + d) <= getLastDividerPosition()) {
                            leftFilters.setMaxWidth(244.0);
                            setCurrentDividerPosition(getCurrentDividerPosition() + d);
                            //System.out.println("interesting 1");
                        } else { //DONE

                            //System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            //leftFilters.setMaxWidth(244.0);
                            restoreContentSize();
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            // leftFilters.setMaxWidth(244.0);
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            setCurrentDividerPosition(0.0);
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            stop();
                            // System.out.println("test 1");
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                        }
                    } // intent to slide out content:
                    else {
                        if (getCurrentDividerPosition() > 0.0) {
                            setCurrentDividerPosition(getCurrentDividerPosition() - d);
                            //System.out.println("interesting 2");
                        } else { //DONE
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            setCurrentDividerPosition(0.0);
                            //System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            stop();
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                            leftFilters.setMaxWidth(0.0);
                            // System.out.println("test 2");
                            // System.out.println(leftFilters.getMinWidth()+" "+leftFilters.getMaxWidth()+" "+getCurrentDividerPosition());
                        }
                    }
                    break;
                case RIGHT:
                case DOWN:
                    // intent to slide in content:
                    if (isAimContentVisible()) {
                        if ((getCurrentDividerPosition() - d) >= getLastDividerPosition()) {
                            setCurrentDividerPosition(getCurrentDividerPosition() - d);
                        } else { //DONE
                            restoreContentSize();
                            stop();
                        }
                    } // intent to slide out content:
                    else {
                        if (getCurrentDividerPosition() < 1.0) {
                            setCurrentDividerPosition(getCurrentDividerPosition() + d);
                        } else {//DONE
                            setCurrentDividerPosition(1.0);
                            stop();
                        }
                    }
                    break;
            }
        }
    }
}
