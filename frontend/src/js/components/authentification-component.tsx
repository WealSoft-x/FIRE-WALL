import * as React from 'react'
import Avatar from '@mui/material/Avatar'
import Button from '@mui/material/Button'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import FormControlLabel from '@mui/material/FormControlLabel'
import Checkbox from '@mui/material/Checkbox'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import axios from 'axios'
import { useState } from 'react'
import { Constants } from '../constants/constants'
import { useHistory } from 'react-router-dom'

function Copyright(props: any) {
  return (
    <Typography variant='body2' color='text.secondary' align='center' {...props}>
      {'Copyright © '}
      <Link color='inherit' href='https://wealsoft.co.jp/'>
        wealsoft
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  )
}

const theme = createTheme()

export default function AuthentificationComponent() {
  const [token, setToken] = useState('');
  const history = useHistory();

    const handleSubmit = (event: any) => {
        event.preventDefault();
      axios
        .post(`${Constants.BACKEND_URL}/authentification`, {
          token: token
        },  
        {
          headers: {
            'Content-Type': 'application/json',
          }
        })
        .then((response) => {
          console.log(response)
          if (response.data == "ログイン画面へ：トークン時間切れ") {
            alert(response.data);
            history.push('/fire-wall')
          } else if (response.data == "2段階認証画面へ: トークンが違います") {
            alert(response.data)
          } else {
            alert('OK')
          }
        })
        .catch((e) => {
            console.log(e)
        })
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component='main' maxWidth='xs'>
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component='h1' variant='h5'>
            Sign in
          </Typography>
          <Box component='form' onSubmit={(e: any) => handleSubmit(e)} noValidate sx={{ mt: 1 }}>
            <TextField
              margin='normal'
              required
              fullWidth
              id='token'
              label='token'
              name='token'
              autoComplete='token'
              autoFocus
              onChange={(e) => setToken(e.target.value)}
            />         
            <Button type='submit' fullWidth variant='contained' sx={{ mt: 3, mb: 2 }}>
              Sign In
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  )
}
