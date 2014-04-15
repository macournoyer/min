package min.lang;

import java.util.ArrayList;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class Bootstrap {

    public void run() throws MinException {

        // Bootstrap base objects
        MinObject base = new MinObject();
        MinObject object = base.clone();
        MinObject lobby = base.clone();
        object.prependProto(lobby);

        MinObject.object = object;
        MinObject.string = object.clone().with("");

        // Introduce objects into Lobby
        MinObject.base = lobby.setSlot("Base", base);
        MinObject.lobby = lobby.setSlot("Lobby", lobby);

        lobby.setSlot("$", lobby);
        lobby.setSlot("Object", MinObject.object);
        lobby.setSlot("String", MinObject.string);

        MinObject.base.asKind("Base");
        MinObject.lobby.asKind("Lobby");
        MinObject.object.asKind("Object");
        MinObject.string.asKind("String");

        MinObject.call = lobby.setSlot("Call", object.clone().asKind("Call"));
        MinObject.method = lobby.setSlot("Method", object.clone().asKind("Method"));
        MinObject.message = lobby.setSlot("Message", object.clone().asKind("Message"));
        MinObject.number = lobby.setSlot("Number", object.clone().with(0).asKind("Number"));
        MinObject.array = lobby.setSlot("Array", object.clone().with(new ArrayList<MinObject>()).asKind("Array"));
        MinObject.nil = lobby.setSlot("nil", object.clone().with(null).asKind("nil"));
        MinObject._true = lobby.setSlot("true", object.clone().with(true).asKind("true"));
        MinObject._false = lobby.setSlot("false", object.clone().with(false).asKind("false"));

        // Load properties
        Properties properties = loadProperties();

        // Set load path
        MinObject.lobby.slot("load_path", MinObject.newArray(new MinObject[]{
                MinObject.newString("."),
                MinObject.newString(properties.getProperty("lib.path"))
        }));

        /////////// Add core slots to objects ///////////

        // Base
        MinObject.base.
                slot("=", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return call.receiver.setSlot(call.args.get(0).name, call.evalArg(1));
                    }
                }).
                slot("set_slot", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return call.receiver.setSlot(call.evalArg(0).getDataAsString(), call.evalArg(1));
                    }
                }).
                slot("get_slot", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return call.receiver.getSlot(call.evalArg(0).getDataAsString());
                    }
                }).
                slot("method", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return new Method(call);
                    }
                }).
                slot("require", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        String file = call.evalArg(0).getDataAsString();
                        return MinObject.require(file);
                    }
                }).
                slot("while", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        while (call.evalArg(0).isTrue()) {
                            call.evalArg(1);
                        }
                        return MinObject.nil;
                    }
                }).
                slot("if", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        if (call.evalArg(0).isTrue()) {
                            return call.evalArg(1);
                        }
                        return MinObject.nil;
                    }
                }).
                slot("not", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newBool(!call.evalArg(0).isTrue());
                    }
                }).
                slot("do", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        call.args.get(0).evalOn(call.receiver);
                        return call.receiver;
                    }
                }).
                slot("pass", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.nil;
                    }
                }).
                slot("puts", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        System.out.println(call.evalArg(0).getData());
                        return call.receiver;
                    }
                }).
                slot("print", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        System.out.print(call.evalArg(0).getData());
                        return call.receiver;
                    }
                });

        // Object
        MinObject.object.
                slot("inspect", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newString(call.receiver.toString());
                    }
                }).
                slot("clone", new Method() {
                    public MinObject activate(Call call) {
                        return call.receiver.clone();
                    }
                }).
                slot("object_id", new Method() {
                    public MinObject activate(Call call) {
                        return MinObject.newNumber(call.receiver.getObjectID());
                    }
                }).
                slot("protos", new Method() {
                    public MinObject activate(Call call) {
                        return MinObject.newArray(call.receiver.protos);
                    }
                }).
                slot("inspect", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newString(call.receiver.toString());
                    }
                }).
                slot("new", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        MinObject c = call.receiver.clone();
                        call.receiver = c;
                        if (c.hasSlot("initialize")) c.getSlot("initialize").activate(call);
                        return c;
                    }
                }).
                slot("==", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newBool(call.receiver.getData().equals(call.evalArg(0).getData()));
                    }
                }).
                slot("!=", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newBool(!call.receiver.getData().equals(call.evalArg(0).getData()));
                    }
                });

        // nil
        MinObject.nil.
                slot("==", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        MinObject arg = call.evalArg(0);
                        return MinObject.newBool(arg == MinObject.nil);
                    }
                }).
                slot("!=", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        MinObject arg = call.evalArg(0);
                        return MinObject.newBool(arg != MinObject.nil);
                    }
                });


        // String
        MinObject.string.
                slot("+", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newString(call.receiver.getDataAsString() + call.evalArg(0).getDataAsString());
                    }
                }).
                slot("size", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsString().length());
                    }
                });

        // Number
        MinObject.number.
                slot("+", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsNumber() + call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot("-", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsNumber() - call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot("*", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsNumber() * call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot("/", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsNumber() / call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot(">", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newBool(call.receiver.getDataAsNumber() > call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot("<", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newBool(call.receiver.getDataAsNumber() < call.evalArg(0).getDataAsNumber());
                    }
                });

        // Array
        MinObject.array.
                slot("initialize", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        ArrayList<MinObject> items = new ArrayList<MinObject>();
                        call.receiver.data = items;
                        for (Message arg : call.args) items.add(arg.evalOn(call.base));
                        return MinObject.nil;
                    }
                }).
                slot("at", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        int at = call.evalArg(0).getDataAsNumber();
                        ArrayList<MinObject> array = call.receiver.getDataAsArray();
                        if (at >= array.size()) return MinObject.nil;
                        return array.get(at);
                    }
                }).
                slot("size", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return MinObject.newNumber(call.receiver.getDataAsArray().size());
                    }
                }).
                slot("push", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        MinObject obj = call.evalArg(0);
                        call.receiver.getDataAsArray().add(obj);
                        return obj;
                    }
                }).
                slot("pop", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        ArrayList<MinObject> array = call.receiver.getDataAsArray();
                        if (array.isEmpty()) return MinObject.nil;
                        return array.remove(0);
                    }
                }).
                slot("shift", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        ArrayList<MinObject> array = call.receiver.getDataAsArray();
                        if (array.isEmpty()) return MinObject.nil;
                        return array.remove(array.size() - 1);
                    }
                }).
                slot("each", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        ArrayList<MinObject> array = call.receiver.getDataAsArray();
                        if (call.args.size() == 2) { // each(i): body
                            String iName = call.args.get(0).name;
                            for (MinObject i : array) {
                                call.base.setSlot(iName, i);
                                call.evalArg(1);
                            }
                            call.base.removeSlot(iName);
                        } else if (call.args.size() == 1) { // each(body)
                            for (MinObject i : array) call.args.get(0).evalOn(i);
                        } else {
                            throw new MinException("Wrong number of arguments");
                        }
                        return call.receiver;
                    }
                });

        // Message
        MinObject.message.
                slot("new", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        return Message.parse(call.evalArg(0).getDataAsString());
                    }
                }).
                slot("name", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return MinObject.newString(m.name);
                    }
                }).
                slot("full_name", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return MinObject.newString(m.fullName());
                    }
                }).
                slot("prev", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.prev;
                    }
                }).
                slot("next", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.next;
                    }
                }).
                slot("args", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return MinObject.newArray(m.args.toArray(new MinObject[0]));
                    }
                }).
                slot("last?", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return MinObject.newBool(m.isLast());
                    }
                }).
                slot("operator?", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return MinObject.newBool(m.isOperator());
                    }
                }).
                slot("tail", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.tail();
                    }
                }).
                slot("insert", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.insert((Message) call.evalArg(0));
                    }
                }).
                slot("append", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.append((Message) call.evalArg(0));
                    }
                }).
                slot("detatch", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.detatch();
                    }
                }).
                slot("pop", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.pop();
                    }
                }).
                slot("eval_on", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Message m = (Message) call.receiver;
                        return m.evalOn(call.evalArg(0));
                    }
                });

        // Call
        MinObject.call.
                slot("eval_arg", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Call c = (Call) call.receiver;
                        return c.evalArg(call.evalArg(0).getDataAsNumber());
                    }
                }).
                slot("args", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Call c = (Call) call.receiver;
                        return MinObject.newArray(c.args.toArray(new MinObject[0]));
                    }
                }).
                slot("receiver", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Call c = (Call) call.receiver;
                        return c.receiver;
                    }
                }).
                slot("base", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Call c = (Call) call.receiver;
                        return c.base;
                    }
                }).
                slot("message", new Method() {
                    public MinObject activate(Call call) throws MinException {
                        Call c = (Call) call.receiver;
                        return c.message;
                    }
                });

        MinObject.require("bootstrap");
    }

    private Properties loadProperties() throws MinException {
        Properties properties = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("default.properties");
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new MinException(e);
        }
    }
}