const Webpack = require('webpack')
const path = require('path')
const src_path = path.resolve(__dirname, 'src')
const public_path = path.resolve(__dirname, 'public')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
const FixStyleOnlyEntriesPlugin = require('webpack-fix-style-only-entries')
const search_dir = 'fire-wall'

module.exports = [
  {
    entry: {
      main: [src_path + '/js/main.tsx'],
      style: [src_path + '/scss/style.scss'],
    },
    output: {
      path: public_path,
      filename: search_dir + '/[name].js',
    },
    module: {
      rules: [
        {
          test: /\.scss$/,
          use: [{ loader: MiniCssExtractPlugin.loader }, { loader: 'css-loader' }, { loader: 'sass-loader' }],
        },
        {
          test: [/\.ts$/, /\.tsx$/],
          use: [
            {
              loader: 'babel-loader',
              options: {
                  presets: ['@babel/preset-env', '@babel/preset-react', '@babel/preset-typescript'],
              },
            },
            'ts-loader',
          ],
          exclude: /node_modules/,
        },
      ],
    },
    plugins: [
      new FixStyleOnlyEntriesPlugin({ silent: true }),
      new Webpack.optimize.AggressiveMergingPlugin(),
      new MiniCssExtractPlugin({ filename: './' + search_dir + '/[name].css' }),
      new Webpack.DefinePlugin({ 'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV) }),
    ],
    resolve: {
      extensions: ['.js', '.jsx', '.ts', '.tsx'],
    },
    optimization: {
      minimizer: [
        new OptimizeCSSAssetsPlugin({}),
        new TerserPlugin({ terserOptions: { compress: { drop_console: false } } }),
      ],
    },
  },
]
