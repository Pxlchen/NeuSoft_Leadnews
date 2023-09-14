// You can see all the config in `./configs`.
const buildPlugins = require('./configs/plugin');
let webpackConfig;

module.exports = env => {
  webpackConfig = {
    module: {
      rules: [
        // 处理CSS文件
        {
          test: /\.css$/,
          use: ['style-loader', 'css-loader'],
        },
      ],
    },
    resolve: {
      alias: {
        'animate.css': 'animate.css/animate.css',
      },
    },
  };

  switch (env.NODE_ENV) {
    case 'prod':
    case 'production':
      webpackConfig = require('./configs/webpack.prod.conf');
      break;
    case 'test':
    case 'testing':
      webpackConfig = require('./configs/webpack.test.conf');
      break;
    case 'plugin':
      buildPlugins();
      break;
    case 'common':
      webpackConfig = require('./configs/webpack.common.conf');
      break;
    case 'release':
      webpackConfig = require('./configs/webpack.release.conf');
      break;
    case 'dev':
    case 'development':
    default:
      webpackConfig = require('./configs/webpack.dev.conf');
  }

  return webpackConfig;
};
