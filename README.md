# SkVaniject
## 説明
多分需要ないです。もしかしたら代用あるかもしれません。
コードの可読性は低めですわよ
<br />
It is a plugin that allows you to operate objectives on the vanilla scoreboard with Skript.Most Skript add-ons have their own scoreboard, so they can't take over the vanilla scoreboard objectives.
<br />
SkriptでバニラのスコアボードのObjectiveを操作することができるようになるプラグインです。ほとんどのSkアドオンには独自のスコアボードを使ってるのでバニラのスコアボードのObjectiveを引き継ぐことはできないと思ったので作りました。

## Documents

### Get Objective (Objectiveの取得) - [expression]
コマンド「/scoreboard objectives add ...」、このアドオンでつくったバニラのObjectiveを取得できます。

#### 書式
```yml
objective[s] [(named|with name[s])] %string%
```

#### 例
```yml
new objective "example" with display name "Example" with criteria "dummy"
set {_name} to name of objective "example"
send "%{_name}%"
# Exampleと出力されます。
```

### Creating a new Objective (Objectiveの新規作成) - [effect & expression]
コマンドでは「/scoreboard objectives add &lt;オブジェクト名&gt; &lt;条件&gt; &lt;表示名&gt;」という方法が用意されています。<br />
それをSkriptで処理ができるだけです。
expressionも用意しているのでsetでそのまま代入することも可能です。

#### 書式
```yml
[skvaniject] new objective[s] [(named|with [the] name)] %string% [(display( |-)named|with [the] display( |-)name) %string%] [with [the] criteria %string%]
```
criteria %string%の%string%部分はコマンドの「条件」の部分と同じだと思います。

#### 例
```yml
new objective "example" with display name "Example" with criteria "dummy"
```

### Delete Objective (Objectiveの削除) - [effect]
コマンドでは「/scoreboard objectives remove &lt;オブジェクト名&gt;」という方法が用意されています。<br />
それをSkriptで処理ができるだけです。

#### 書式
```yml
delete %objective%
```

#### 例
```yml
delete objective named "example"
```

### Get name of Objective (Objectiveの名前取得) - [expression]
ほぼ使う機会はないと思います。
取得するだけで名前変更はできません。

#### 書式
```yml
name of %objective%
```

#### 例
```yml
set {_example} to new objective "example" with display name "Example" with criteria "dummy"
send "%name of {_example}%"
# 「example」と出力されます。
```

### Display name of Objective (Objectiveの表示名) - [expression]
表示名を取得したり途中で変更したりできます。

#### 書式
```yml
display name of %objective%
```

#### 例
```yml
set display name of objective "example" to "Hello, World!"
```

#### 例2
```yml
set {_example} to new objective "example" with display name "Example" with criteria "dummy"
set {_displayname} to display name of {_example}
send "%{_displayname}%"
# 「Example」と出力されます。
```

### Get criteria of Objective (Objectiveの条件取得) - [expression]

#### 書式
```yml
criteria of %objective%
```

#### 例
```yml
set {_criteria} to criteria of objective "example"
send "%{_criteria}%"
# dummyならばdummyが出力される。
```

### Value of Objective of player (プレイヤーのObjectiveの数値取得) - [expression]
プレイヤー以外にもエンティティや文字列も可能です。
#### 書式
```yml
value of %objective% of %string%
```

#### 例
```yml
set {_example} to new objective "example" with display name "Example" with criteria "dummy"
set value of {_example} of "%player%" to 500
set {_value} to value of {_example} of "%player%"
send "%{_value}%"
# 500が出力される。
```
最後の引数はstring型なので"aiueo"でも可能です。

### Objective is exist/set (Objectiveの存在チェック) - [condition]

#### 書式
```yml
objective[s] [(named|with [the] name)] %string% (1¦(is|=|do|does)|2¦(is|do|does)(n't|[ ]not)) (exist|set)
```

#### 例
```yml
new objective "example" with display name "Example" with criteria "dummy"
if objective named "example" is set:
    send "Example existed!"
```
## Q&A
### Q.プレイヤーのオブジェクトの存在チェックはできないの？
- A.サポートはしていませんが、一応可能です。<br />noneが返ってくるのでif value of objective named "example" of "%player%" is set:を使えば可能となります。
