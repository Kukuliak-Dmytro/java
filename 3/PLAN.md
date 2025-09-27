# План розробки гри "Битва дроїдів"

## Загальний опис
Розробка консольної гри "Битва дроїдів" на мові Java з реалізацією ООП принципів.

## 1. Мінімальні вимоги

### 1.1 Базовий клас Droid
- Створити абстрактний клас з характеристиками: name, health, damage
- Додати методи для атаки, отримання урону, перевірки життя

### 1.2 Підкласи дроїдів
- Реалізувати мінімум 3-4 різні види дроїдів
- Кожний вид має унікальні характеристики та здібності

### 1.3 Види бою
- Бій 1 на 1
- Бій команда на команду

### 1.4 Структура пакетів
Організувати класи по пакетах:
- `droids` - класи дроїдів
- `battle` - логіка бою
- `menu` - консольний інтерфейс
- `utils` - допоміжні класи

### 1.5 Консольне меню
Мінімальний набір команд:
- Створити дроїда
- Показати список дроїдів
- Запустити бій 1 на 1
- Запустити бій команда на команду
- Запустити автоматичний дуель 1 на 1
- Запустити автоматичний дуель 3 на 3
- Записати бій у файл
- Відтворити бій з файлу
- Вийти з програми

## 2. Етапи реалізації

### Етап 1: Основна архітектура (1 заняття)
1. Створити структуру пакетів
2. Реалізувати базовий клас Droid
3. Створити 2-3 підкласи дроїдів з базовими характеристиками
4. Реалізувати просту логіку бою 1 на 1
5. Створити базове консольне меню

### Етап 2: Розширення функціоналу (1 заняття)
1. Реалізувати бій команда на команду
2. Додати автоматичні дуелі (1v1 та 3v3)
3. Додати систему збереження/завантаження боїв
4. Розширити меню всіма необхідними функціями
5. Додати додаткові види дроїдів

### Етап 3: Поліпшення та тестування (1 заняття)
1. Додати додаткові фічі (арени, спецздібності)
2. Збалансувати характеристики дроїдів
3. Покращити інтерфейс користувача
4. Тестування та виправлення багів
5. Документація та фінальна перевірка

## 3. Деталізовані вимоги до класів

### 3.1 Клас Droid (абстрактний)
```
Поля:
- String name
- int health
- int maxHealth
- int damage
- double accuracy
- int speed (нова характеристика для системи ініціативи)
- List<Effect> activeEffects (список активних ефектів)

Методи:
- attack(Droid target)
- takeDamage(int damage)
- isAlive()
- heal(int amount)
- getSpeed()
- applyEffect(Effect effect)
- removeEffect(Effect effect)
- updateEffects() (оновлення ефектів кожен хід)
- getInfo()
```

### 3.1.1 Система ефектів
```
Клас Effect (абстрактний):
- String name
- int duration (тривалість в ходах)
- EffectType type (BUFF, DEBUFF, SHIELD тощо)

Підкласи ефектів:
- DamageBuffEffect: збільшує урон на X%
- SpeedBuffEffect: збільшує швидкість на X%
- ShieldEffect: зменшує отримуваний урон на X%
- DamageReductionEffect: зменшує урон ворогів на X%
- PoisonEffect: завдає урон кожен хід (для AssassinDroid)
- HealOverTimeEffect: лікує кожен хід
```

### 3.2 Підкласи дроїдів

#### WarriorDroid
- Високий урон (30-40)
- Середнє здоров'я (120-150)
- Висока точність (0.85)
- Середня швидкість (12-15)
- **Спеціальні здібності**: 
  - Критичний удар (15% шанс подвійного урону)
  - Берсерк (при HP < 30% отримує +50% урону)

#### TankDroid
- Низький урон (15-25)
- Високе здоров'я (200-250)
- Середня точність (0.75)
- Низька швидкість (8-10)
- **Спеціальні здібності**:
  - Блокування атак (25% шанс зменшити урон наполовину)
  - Провокація (може змусити ворога атакувати себе)

#### SupportDroid (базовий абстрактний клас підтримки)
```java
public abstract class SupportDroid extends Droid {
    // Загальні характеристики для всіх Support дроїдів
    // Низький урон (8-15)
    // Середнє здоров'я (90-120)
    // Висока точність (0.90)
    // Висока швидкість (16-20)
    
    // Абстрактні методи, які мають реалізувати підкласи
    public abstract void useSpecialAbility(List<Droid> allies, List<Droid> enemies);
    public abstract boolean canUseAbility();
    public abstract String getAbilityDescription();
}
```

#### MedicDroid extends SupportDroid
- Урон (10-18)
- Здоров'я (100-130)
- **Спеціальні здібності**:
  - Лікування союзників (відновлює 25-40 HP)
  - Масове лікування (лікує всю команду на 15-25 HP)

#### ShieldDroid extends SupportDroid  
- Урон (8-15)
- Здоров'я (110-140)
- **Спеціальні здібності**:
  - Щит (дає союзнику +50% захист на 3 ходи)
  - Енергетичний бар'єр (захищає всю команду на 1 хід)

#### BufferDroid extends SupportDroid
- Урон (12-20)
- Здоров'я (95-125)  
- **Спеціальні здібності**:
  - Підсилення урону (+30% урону союзнику на 4 ходи)
  - Підвищення швидкості (+25% швидкості команді на 3 ходи)

#### DefenderDroid extends SupportDroid
- Урон (6-12)
- Здоров'я (120-150)
- **Спеціальні здібності**:
  - Зменшення урону (-40% урону ворогам на 3 ходи)
  - Перенаправлення атак (перенаправляє атаки на себе)

#### AssassinDroid
- Дуже високий урон (40-60)
- Низьке здоров'я (80-100)
- Дуже висока точність (0.95)
- Дуже висока швидкість (20-25)
- **Спеціальні здібності**:
  - Уникнення атак (20% шанс повністю уникнути урону)
  - Отруєння (атаки завдають додатковий урон протягом 3 ходів)

### 3.3 Клас Battle
```
Методи:
- oneOnOneBattle(Droid droid1, Droid droid2)
- teamBattle(List<Droid> team1, List<Droid> team2)
- automaticDuel1v1(List<Droid> availableDroids)
- automaticDuel3v3(List<Droid> availableDroids)
- calculateInitiativeOrder(List<Droid> participants)
- processSpeedBasedTurns(List<Droid> participants)
- selectRandomTarget(List<Droid> enemies)
- saveBattleLog(String filename)
- loadBattleLog(String filename)
- displayBattle()
```

### 3.3.1 Система покрокової атаки на основі швидкості
```
Клас InitiativeSystem:
- calculateTurnOrder(List<Droid> participants) - розрахунок черги ходів
- getNextDroid() - визначення наступного дроїда для ходу
- processTurn(Droid attacker, List<Droid> targets) - обробка ходу

Алгоритм:
1. Створюється "часова лінія" для кожного дроїда
2. Кожен дроїд отримує наступний хід через (базовий_час / швидкість)
3. Дроїди ходять у порядку їх часової черги
4. Після ходу дроїд отримує наступний слот часу

Приклади:
- Швидкість 20 vs 10: співвідношення 2:1 (швидший ходить двічі)  
- Швидкість 15 vs 10: співвідношення 3:2 (15-10-15-15-10-15...)
- Швидкість 12 vs 8: співвідношення 3:2 (12-8-12-12-8-12...)
```

### 3.4 Клас GameMenu
```
Методи:
- showMainMenu()
- createDroid()
- showDroidList()
- startOneOnOneBattle()
- startTeamBattle()
- startAutomaticDuel1v1()
- startAutomaticDuel3v3()
- saveBattle()
- loadBattle()
- exit()
```

## 4. Додаткові можливості (бонуси)

### 4.1 Арени
- Desert Arena: -10% точність для всіх
- Space Station: +15% урон для всіх
- Medical Bay: +5 HP регенерації на хід для всіх
- Gravity Chamber: +20% урон, -10% швидкість
- Speed Track: +15% швидкість всім дроїдам
- Maze Arena: -20% швидкість, +10% точність

### 4.2 Стратегії бою команди на команду
- Random: випадковий вибір цілей (для автоматичних дуелів)
- Focused: атака найслабшого
- Balanced: атака по черзі
- Protect Support: захист лікарів
- Speed Priority: першочергова атака найшвидших ворогів

### 4.3 Автоматичні дуелі
- **Дуель 1 на 1**: Система автоматично вибирає 2 випадкових дроїдів
- **Дуель 3 на 3**: Система формує 2 команди по 3 дроїди кожна
- **Рандомні атаки**: Кожен дроїд атакує випадкову ціль з ворожої команди
- **Автоматична швидкість**: Можливість прискорити/сповільнити бій
- **Статистика турніру**: Підрахунок перемог кожного дроїда
- **Серії боїв**: Можливість провести кілька автоматичних боїв підряд

### 4.4 Система покрокової атаки - переваги
- Реалістичніша тактика (швидкі дроїди більш ефективні)
- Збалансованість через trade-off (швидкість vs здоров'я/урон)
- Можливість планувати стратегію на основі швидкості команди
- Додає глибину вибору типів дроїдів

### 4.5 Статистика
- Кількість перемог/поразок
- Завданий/отриманий урон
- Час виживання в бою
- Ефективність використання зброї

## 5. Файлова структура проекту

```
src/
├── Main.java
├── droids/
│   ├── Droid.java
│   ├── WarriorDroid.java
│   ├── TankDroid.java
│   ├── SupportDroid.java
│   ├── MedicDroid.java
│   ├── ShieldDroid.java
│   ├── BufferDroid.java
│   ├── DefenderDroid.java
│   └── AssassinDroid.java
├── battle/
│   ├── Battle.java
│   ├── AutomaticDuel.java
│   ├── InitiativeSystem.java
│   ├── Arena.java
│   └── BattleStrategy.java
├── effects/
│   ├── Effect.java
│   ├── DamageBuffEffect.java
│   ├── SpeedBuffEffect.java
│   ├── ShieldEffect.java
│   ├── DamageReductionEffect.java
│   ├── PoisonEffect.java
│   └── HealOverTimeEffect.java
├── menu/
│   └── GameMenu.java
└── utils/
    ├── FileManager.java
    ├── BattleLogger.java
    └── InputValidator.java
```

## 6. Ієрархія Support дроїдів - детальний опис

### 6.1 Повна ієрархія успадкування
```java
// Базова ієрархія
Droid (abstract)
├── WarriorDroid extends Droid
├── TankDroid extends Droid  
├── AssassinDroid extends Droid
└── SupportDroid (abstract) extends Droid
    ├── MedicDroid extends SupportDroid
    ├── ShieldDroid extends SupportDroid
    ├── BufferDroid extends SupportDroid
    └── DefenderDroid extends SupportDroid
```

### 6.2 Базовий клас SupportDroid
```java
public abstract class SupportDroid extends Droid {
    protected int supportRange = 1; // дальність підтримки
    protected int manaCost = 20;    // вартість здібностей
    protected int currentMana = 100; // поточна мана
    protected int maxMana = 100;    // максимальна мана
    
    // Конструктор для всіх Support дроїдів
    public SupportDroid(String name, int health, int damage, double accuracy, int speed) {
        super(name, health, damage, accuracy, speed);
        this.currentMana = maxMana;
    }
    
    // Абстрактні методи, які ПОВИННІ реалізувати ВСІ підкласи
    public abstract void useSpecialAbility(List<Droid> allies, List<Droid> enemies);
    public abstract boolean canUseAbility();
    public abstract String getAbilityDescription();
    public abstract int getManaCost();
    
    // Загальні методи для всіх Support дроїдів
    protected Droid selectBestAlly(List<Droid> allies) {
        // Логіка вибору найкращого союзника для підтримки
        return allies.stream()
            .filter(Droid::isAlive)
            .min((d1, d2) -> Integer.compare(d1.getHealth(), d2.getHealth()))
            .orElse(null);
    }
    
    protected void consumeMana(int cost) {
        this.currentMana = Math.max(0, this.currentMana - cost);
    }
    
    protected void restoreMana(int amount) {
        this.currentMana = Math.min(maxMana, this.currentMana + amount);
    }
    
    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
}
```

### 6.3 MedicDroid extends SupportDroid
```java
public class MedicDroid extends SupportDroid {
    public MedicDroid(String name) {
        super(name, 115, 14, 0.90, 18); // середні характеристики
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (canUseAbility()) {
            Droid target = selectBestAlly(allies);
            if (target != null) {
                int healAmount = 25 + random.nextInt(16); // 25-40 HP
                target.heal(healAmount);
                consumeMana(getManaCost());
            }
        }
    }
    
    @Override
    public int getManaCost() { return 25; }
}
```
- **Роль**: Основний лікар команди
- **Тактика**: Тримається позаду, лікує поранених
- **Пріоритети цілей**: Союзники з найменшим HP → інші Support дроїди → себе

### 6.4 ShieldDroid extends SupportDroid
```java
public class ShieldDroid extends SupportDroid {
    public ShieldDroid(String name) {
        super(name, 125, 11, 0.90, 17); // більше HP, менше урону
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (canUseAbility()) {
            Droid target = selectBestAlly(allies);
            if (target != null) {
                ShieldEffect shield = new ShieldEffect(3, 0.5); // 50% захист на 3 ходи
                target.applyEffect(shield);
                consumeMana(getManaCost());
            }
        }
    }
    
    @Override
    public int getManaCost() { return 30; }
}
```
- **Роль**: Захисний спеціаліст
- **Тактика**: Створює бар'єри та щити
- **Пріоритети цілей**: Танки що атакують → пошкоджені союзники → інші Support дроїди

### 6.5 BufferDroid extends SupportDroid
```java
public class BufferDroid extends SupportDroid {
    public BufferDroid(String name) {
        super(name, 110, 16, 0.90, 19); // більше урону і швидкості
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (canUseAbility()) {
            Droid target = selectBestAttacker(allies); // вибирає найкращого атакера
            if (target != null) {
                DamageBuffEffect buff = new DamageBuffEffect(4, 0.3); // +30% урону на 4 ходи
                target.applyEffect(buff);
                consumeMana(getManaCost());
            }
        }
    }
    
    @Override
    public int getManaCost() { return 35; }
}
```
- **Роль**: Тактичний підсилювач команди
- **Тактика**: Підсилює урон та швидкість союзників
- **Пріоритети цілей**: Атакуючі дроїди (Warrior, Assassin) → танки в бою → себе

### 6.6 DefenderDroid extends SupportDroid
```java
public class DefenderDroid extends SupportDroid {
    public DefenderDroid(String name) {
        super(name, 135, 9, 0.90, 16); // найбільше HP, найменший урон
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (canUseAbility()) {
            Droid target = selectMostDangerous(enemies); // найнебезпечніший ворог
            if (target != null) {
                DamageReductionEffect debuff = new DamageReductionEffect(3, 0.4); // -40% урону на 3 ходи
                target.applyEffect(debuff);
                consumeMana(getManaCost());
            }
        }
    }
    
    @Override
    public int getManaCost() { return 30; }
}
```
- **Роль**: Ослаблення ворогів та контроль
- **Тактика**: Зменшує ефективність ворожої команди
- **Пріоритети цілей**: Найнебезпечніші вороги → ворожі Support дроїди → групові дебаффи

### 6.7 Переваги ієрархії успадкування Support дроїдів

#### Загальний код в базовому класі:
- **Система мани**: Всі Support дроїди використовують ману для здібностей
- **Вибір цілей**: Загальні алгоритми для вибору союзників/ворогів
- **Управління ефектами**: Єдиний підхід до застосування ефектів
- **Перевірки**: Загальна логіка перевірки можливості використання здібностей

#### Поліморфізм:
```java
// Можна працювати з усіма Support дроїдами однаково
List<SupportDroid> supportTeam = Arrays.asList(
    new MedicDroid("Medic-1"),
    new ShieldDroid("Shield-1"), 
    new BufferDroid("Buffer-1")
);

// Кожен дроїд використає свою унікальну реалізацію
for (SupportDroid support : supportTeam) {
    support.useSpecialAbility(allies, enemies); // поліморфний виклик
}
```

#### Розширюваність:
- Легко додати нові типи Support дроїдів
- Всі нові класи автоматично отримають базову функціональність
- Можна перевизначити тільки потрібні методи

### 6.8 Тактичні комбінації Support команд
- **Медична група**: 2x MedicDroid + 1x ShieldDroid
- **Агресивна підтримка**: 1x BufferDroid + 1x DefenderDroid + 1x MedicDroid  
- **Оборонна**: 2x ShieldDroid + 1x DefenderDroid
- **Збалансована**: по одному кожного типу

## 7. Автоматичні дуелі - детальний опис

### 6.1 Автоматичний дуель 1 на 1
```java
// Псевдокод автоматичного дуелю
class AutomaticDuel {
    public void startDuel1v1(List<Droid> availableDroids) {
        // 1. Вибрати 2 випадкових дроїдів
        Droid fighter1 = selectRandomDroid(availableDroids);
        Droid fighter2 = selectRandomDroid(availableDroids, fighter1);
        
        // 2. Ініціалізувати систему ініціативи
        InitiativeSystem initiative = new InitiativeSystem();
        initiative.addParticipants(Arrays.asList(fighter1, fighter2));
        
        // 3. Проводити бій до переможця
        while (fighter1.isAlive() && fighter2.isAlive()) {
            Droid currentFighter = initiative.getNextDroid();
            Droid target = (currentFighter == fighter1) ? fighter2 : fighter1;
            
            performAttack(currentFighter, target);
            displayTurn(currentFighter, target);
        }
        
        // 4. Оголосити переможця та оновити статистику
        announceWinner(fighter1.isAlive() ? fighter1 : fighter2);
    }
}
```

### 6.2 Автоматичний дуель 3 на 3
```java
public void startDuel3v3(List<Droid> availableDroids) {
    // 1. Сформувати 2 команди по 3 дроїди
    List<Droid> team1 = selectRandomTeam(availableDroids, 3);
    List<Droid> team2 = selectRandomTeam(availableDroids, 3, team1);
    
    // 2. Ініціалізувати систему ініціативи для всіх учасників
    InitiativeSystem initiative = new InitiativeSystem();
    initiative.addParticipants(team1);
    initiative.addParticipants(team2);
    
    // 3. Проводити бій до повного знищення однієї команди
    while (hasAliveDroids(team1) && hasAliveDroids(team2)) {
        Droid currentFighter = initiative.getNextDroid();
        
        // Визначити ворожу команду та вибрати випадкову ціль
        List<Droid> enemyTeam = team1.contains(currentFighter) ? team2 : team1;
        Droid target = selectRandomAliveTarget(enemyTeam);
        
        performAttack(currentFighter, target);
        displayTurn(currentFighter, target);
        
        // Видалити мертвих дроїдів з черги ініціативи
        if (!target.isAlive()) {
            initiative.removeDroid(target);
        }
    }
    
    // 4. Оголосити команду-переможця
    List<Droid> winnerTeam = hasAliveDroids(team1) ? team1 : team2;
    announceTeamWinner(winnerTeam);
}
```

### 6.3 Особливості рандомних атак
- **Випадковий вибір цілі**: Кожен дроїд атакує випадково обрану живу ціль
- **Непередбачуваність**: Неможливо передбачити хід бою
- **Реалістичність**: Імітує хаос реального бою
- **Збалансованість**: Всі дроїди мають рівні шанси бути атакованими

### 6.4 Додаткові можливості автоматичних дуелів
- **Швидкість відтворення**: Налаштування затримки між ходами
- **Пауза/продовження**: Можливість зупинити та продовжити бій
- **Детальний лог**: Запис кожного ходу для подальшого аналізу
- **Турнірний режим**: Серія автоматичних боїв з підрахунком очок

## 7. Детальний опис системи покрокової атаки

### 6.1 Принцип роботи
Система базується на швидкості дроїдів і працює як "часова лінія", де кожен дроїд отримує ходи пропорційно до своєї швидкості.

### 6.2 Алгоритм обчислення черги ходів
```java
// Псевдокод алгоритму
class InitiativeSystem {
    private int timeStep = 100; // базовий час для розрахунків
    
    // Розрахунок наступного ходу для дроїда
    private int calculateNextTurn(Droid droid, int currentTime) {
        return currentTime + (timeStep / droid.getSpeed());
    }
    
    // Приклади розрахунків:
    // Швидкість 20: наступний хід через 100/20 = 5 тіків
    // Швидкість 10: наступний хід через 100/10 = 10 тіків  
    // Швидкість 15: наступний хід через 100/15 = 6.67 тіків
}
```

### 6.3 Приклади роботи системи

#### Приклад 1: Швидкість 20 vs 10
```
Тік 0:  Дроїд А (швидкість 20) ходить, наступний хід - тік 5
        Дроїд Б (швидкість 10) ходить, наступний хід - тік 10
Тік 5:  Дроїд А ходить, наступний хід - тік 10  
Тік 10: Дроїд А і Б можуть ходити одночасно, або А трохи раніше
        А: наступний хід - тік 15, Б: наступний хід - тік 20
Тік 15: Дроїд А ходить, наступний хід - тік 20
Тік 20: Дроїд А і Б ходять
```

#### Приклад 2: Швидкість 15 vs 10  
```
Співвідношення: 15:10 = 3:2
За 30 тіків: дроїд А (швидкість 15) зробить 4.5 ходи ≈ 4-5 ходів
            дроїд Б (швидкість 10) зробить 3 ходи
```

### 6.4 Збалансованість системи
- **AssassinDroid**: Швидкість 20-25, але низьке HP - багато ходів, але помирає швидко
- **TankDroid**: Швидкість 8-10, але високе HP - рідко ходить, але довго живе  
- **SupportDroid**: Швидкість 16-20 - часто лікує команду
- **WarriorDroid**: Швидкість 12-15 - збалансований варіант

### 6.5 Тактичні можливості
- Швидка команда може "закидати" повільну атаками
- Повільні танки можуть протистояти, якщо переживуть початковий натиск
- Підтримка стає критично важливою через частоту лікування
- Змішані команди потребують стратегічного планування

## 9. Критерії оцінювання
- Правильна реалізація ООП принципів
- Функціональність всіх вимог
- Якість коду та структура
- Додаткові фічі та креативність
- Збалансованість гри
- Зручність інтерфейсу користувача

## 10. Примітки
- Використовувати Scanner для вводу користувача
- Додати валідацію вводу
- Обробляти винятки (try-catch)
- Коментувати код
- Дотримуватися naming conventions Java