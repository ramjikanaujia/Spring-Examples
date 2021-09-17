package example.acceptance.stories;

import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.IDE_CONSOLE;
import static org.jbehave.core.reporters.Format.TXT;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/storiesContext.xml" })
@ActiveProfiles("test")
public abstract class AbstractSpringJBehaveStory extends JUnitStory implements ApplicationContextAware {

	private static final int STORY_TIMEOUT = 120;

	@Autowired
	private ApplicationContext applicationContext;

	private CrossReference xref = new CrossReference();

	public AbstractSpringJBehaveStory() {
		Embedder embedder = new Embedder();
		embedder.useEmbedderControls(embedderControls());
		embedder.useMetaFilters(Arrays.asList("-skip"));
		useEmbedder(embedder);
	}

	public final void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Configuration configuration() {
		return super.configuration().useStoryPathResolver(storyPathResolver()).useStoryLoader(storyLoader())
				.useStoryReporterBuilder(storyReporterBuilder()).useStepMonitor(xref.getStepMonitor());
	}

	@Override
	public List<CandidateSteps> candidateSteps() {
		return new SpringStepsFactory(configuration(), applicationContext).createCandidateSteps();
	}

	private EmbedderControls embedderControls() {
		return new EmbedderControls().doIgnoreFailureInView(true).useStoryTimeoutInSecs(STORY_TIMEOUT);
	}

	private StoryPathResolver storyPathResolver() {
		return new UnderscoredCamelCaseResolver();
	}

	private StoryLoader storyLoader() {
		return new LoadFromClasspath();
	}

	private StoryReporterBuilder storyReporterBuilder() {
		return new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
				.withPathResolver(new ResolveToPackagedName()).withFailureTrace(true).withFailureTraceCompression(true)
				.withDefaultFormats().withFormats(IDE_CONSOLE, TXT, HTML).withCrossReference(xref);
	}

}
