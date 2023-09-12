'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASEURL:"'/server_85/admin'",
  WS_BASEURL:"'/server_85/WEBSOCKET'",
  WS_SERVER:'localhost:51603'
})
