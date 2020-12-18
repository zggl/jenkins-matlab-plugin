package com.mathworks.ci;

import hudson.Extension;
import hudson.matrix.Axis;
import hudson.matrix.AxisDescriptor;
import hudson.matrix.MatrixProject;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatlabInstallationAxis extends Axis {

    @DataBoundConstructor
    public MatlabInstallationAxis(List<String> values) {
        super(Message.getValue("Axis.matlab.key"), evaluateValues(values));
    }

    static private List<String> evaluateValues(List<String> values) {
        // Add default configuration is values are null or not selected.
        if (values == null || values.isEmpty()) {
            values = new ArrayList<>(Arrays.asList("default"));
        }
        return values;
    }

    @Extension
    public static class DescriptorImpl extends AxisDescriptor{
        private final String useMATLABWarning = Message.getValue("Axis.use.matlab.warning");
        private final String noInstallationError = Message.getValue("Axis.no.installed.matlab.error");

        @Override
        public String getDisplayName() {
            return Message.getValue("Axis.matlab.key");
        }

        @Override
        public boolean isInstantiable() {
            return !checkMatlabInstallationEmpty();
        }

        public boolean checkUseMATLABVersion(Object it) {
            return MatlabItemListener.getMATLABBuildWrapperCheckForPrj(((MatrixProject) it).getFullName()) && !checkMatlabInstallationEmpty();
        }

        public MatlabInstallation[] getInstallations () {
            return MatlabInstallation.getAll();
        }

        public String getUseMATLABWarning() {
            return useMATLABWarning;
        }

        public boolean checkMatlabInstallationEmpty() {
            return MatlabInstallation.isEmpty();
        }

        public String getNoInstallationError() {
            return noInstallationError;
        }
    }
}