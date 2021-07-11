import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import commentComposerFactory from "./commentComposerFactory";
import compactWhitespaces from "./content-refiners/compactWhitespaces";
import trimWhitespaces from "./content-refiners/trimWhitespaces";
import compositeContentRefinerFactory from "./content-refiners/compositeContentRefinerFactory";

const commentRefiner = compositeContentRefinerFactory([
  compactWhitespaces,
  trimWhitespaces
]);

ReactDOM.render(
  <React.StrictMode>
    <App commentComposer={commentComposerFactory(commentRefiner)} />
  </React.StrictMode>,
  document.getElementById("root")
);
