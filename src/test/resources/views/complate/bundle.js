function render(stream, tag, params) {
  stream.writeln(tag);
  stream.writeln(params.title);
  stream.writeln(Array.from("foo").map(function(o, i) {
    return i;
  }));
  stream.flush();
}
