package com.th3l4b.screens.console;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.th3l4b.screens.console.interaction.DefaultInteractionsIdentifierGenerator;
import com.th3l4b.screens.console.interaction.IInteractionIdentifierGenerator;
import com.th3l4b.screens.console.interaction.IInteractionProducer;

public class ConsoleRendererContext {

	private PrintWriter _writer;

	private Locale _locale;

	private IInteractionIdentifierGenerator _interactionIdentifierGenerator = new DefaultInteractionsIdentifierGenerator();
	private Map<String, IInteractionProducer> _interactionProducers = new LinkedHashMap<String, IInteractionProducer>();

	public PrintWriter getWriter() {
		return _writer;
	}

	public void setWriter(PrintWriter writer) {
		_writer = writer;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public IInteractionIdentifierGenerator getInteractionIdentifierGenerator() {
		return _interactionIdentifierGenerator;
	}

	public void setInteractionIdentifierGenerator(
			IInteractionIdentifierGenerator interactionIdentifierGenerator) {
		_interactionIdentifierGenerator = interactionIdentifierGenerator;
	}

	public Map<String, IInteractionProducer> getInteractionProducers() {
		return _interactionProducers;
	}

	public void setInteractionProducers(
			Map<String, IInteractionProducer> interactionProducers) {
		_interactionProducers = interactionProducers;
	}

	public void copyTo(ConsoleRendererContext copy) {
		copy.setWriter(getWriter());
		copy.setLocale(getLocale());
		copy.setInteractionIdentifierGenerator(getInteractionIdentifierGenerator());
		copy.setInteractionProducers(getInteractionProducers());
	}

}
