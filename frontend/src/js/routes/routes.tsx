import React from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import MainContainer from '../containers/container'
import AuthentificationContainer from '../containers/authentification-container'

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
        <Route exact path='/fire-wall/authentification' component={AuthentificationContainer} />
      </Switch>
    </BrowserRouter>
  )
}
