# FIRE-WALL
## 環境設定
### Vagrant
- リアルタイムファイル共有  
vagrant plugin install vagrant-vbguest

### VS Code
- prettier
```
"editor.formatOnSave": true, // ファイル保存時の自動フォーマット有効
"editor.formatOnPaste": true, // ペーストした文字の自動フォーマット有効
"editor.formatOnType": true, // 文字入力行の自動フォーマット有効
"editor.defaultFormatter": "esbenp.prettier-vscode", // デフォルトフォーマッターをPrettierに指定
"editor.codeActionsOnSave": {
    "source.fixAll.eslint": true // ファイル保存時に ESLint でフォーマット
}
```
