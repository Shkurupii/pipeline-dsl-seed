import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertTrue

import com.lesfurets.jenkins.unit.BasePipelineTest
import static com.lesfurets.jenkins.unit.MethodCall.callArgsToString

class TestTemplateJob extends BasePipelineTest {

  @Override
  @Before
  void setUp() throws Exception {
      scriptRoots += 'jobs'
      super.setUp()
      def scmBranch = "feature_test"
      helper.registerAllowedMethod("readFile", [Map.class], {c -> 'bcc19744'})
      binding.setVariable('scm', [
                      $class                           : 'GitSCM',
                      branches                         : [[name: scmBranch]]
      ])
  }

  @Test
  void should_execute_without_errors() throws Exception {
      def script = loadScript("jobs/template/pipeline/template.groovy")
      printCallStack()
  }

  @Test
  void should_print_property_value() {
    def script = loadScript('template/pipeline/template.groovy')

    def expectedValue = 'Hello World!'
    assertTrue(helper.callStack.findAll { call ->
      call.methodName == 'echo'
    }.any { call ->
      callArgsToString(call).contains(expectedValue)
    })
  }

}
