import { routerMiddleware, routerReducer } from 'react-router-redux'
import { createBrowserHistory } from 'history'
import { createStore, applyMiddleware, compose, combineReducers } from 'redux'
import Thunk from 'redux-thunk'
import { createLogger } from 'redux-logger'

export default (rootReducer: any) => {
  const history = createBrowserHistory()
  const logger = createLogger()
  const router = routerMiddleware(history)
  const enhancer = compose(applyMiddleware(router, Thunk, logger))
  const reducers = combineReducers({ rootReducer, routing: routerReducer })
  const store = createStore(reducers, enhancer)

  return store
}
