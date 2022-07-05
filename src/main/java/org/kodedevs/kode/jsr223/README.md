This package provides the `javax.script` integration, which is the preferred way to use Kode. You will ordinarily do
this to obtain an instance of a Kode script engine:

```
import javax.script.*;
...
ScriptEngine kodeEngine = new ScriptEngineManager().getEngineByName("Kode");
```

See [KodeScriptEngineFactory](KodeScriptEngineFactory.java) for further details.
