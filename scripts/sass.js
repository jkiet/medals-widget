
const fs = require("fs");
const sass = require("node-sass");
const R = require("ramda");

const opts = {
  data: fs.readFileSync("resources/public/css/widget.scss").toString(),
  outFile: "widget.css",
  includePaths: ["resources/public/css"],
  outputStyle: "compressed",
  sourceMap: false,
  sourceMapContents: false,
  sourceMapEmbed: false,
  omitSourceMapUrl: true
};

if (R.contains("--sourceMap", process.argv)) {
  opts.sourceMap = true;
  opts.sourceMapContents = true;
  opts.sourceMapEmbed = true;
  opts.omitSourceMapUrl = false;
}

let { css } = sass.renderSync(opts);

fs.writeFileSync("resources/public/css/widget.css", css);