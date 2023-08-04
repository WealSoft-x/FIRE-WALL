import React from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import MainContainer from '../containers/container'

/**
 * Routing
 *
 * @return {*}
 */
export default () => {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path='/fire-wall' component={MainContainer} />
      </Switch>
    </BrowserRouter>
  )
}
