import droids.*;

public class TestMagicDroids {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТ НОВИХ МАГІЧНИХ ДРОЇДІВ ===\n");
        
        // Створюємо нових дроїдів
        PyromancerDroid pyro = new PyromancerDroid("Піромант-F5");
        MageAssassinDroid mageAss = new MageAssassinDroid("Маг-Убивця-M9");
        ElementalGuardianDroid guardian = new ElementalGuardianDroid("Елем.Страж-G2");
        TankDroid tank = new TankDroid("Танк-Т7");
        
        System.out.println("=== ІНФОРМАЦІЯ ПРО ДРОЇДІВ ===");
        System.out.println(pyro.getInfo());
        System.out.println();
        System.out.println(mageAss.getInfo());
        System.out.println();
        System.out.println(guardian.getInfo());
        System.out.println();
        
        // Тест піромант проти танка
        System.out.println("=== ПІРОМАНТ АТАКУЄ ТАНК ===");
        for (int i = 0; i < 3; i++) {
            System.out.println("Атака " + (i + 1) + ":");
            pyro.attack(tank);
            tank.updateEffects();
            System.out.println("HP танка: " + tank.getHealth() + "/" + tank.getMaxHealth());
            System.out.println();
        }
        
        // Спеціальна атака піроманта
        if (pyro.canUseFireBlast()) {
            System.out.println("СПЕЦІАЛЬНА АТАКА ПІРОМАНТА:");
            pyro.fireBlast(tank);
            System.out.println();
        }
        
        // Тест маг-асасина
        System.out.println("=== МАГ-АСАСИН АТАКУЄ ПІРОМАНТА ===");
        for (int i = 0; i < 2; i++) {
            System.out.println("Атака " + (i + 1) + ":");
            mageAss.attack(pyro);
            pyro.updateEffects();
            System.out.println("HP піроманта: " + pyro.getHealth() + "/" + pyro.getMaxHealth());
            System.out.println();
        }
        
        // Тест стража
        System.out.println("=== ПІРОМАНТ АТАКУЄ СТРАЖА ===");
        for (int i = 0; i < 3; i++) {
            System.out.println("Атака " + (i + 1) + ":");
            pyro.attack(guardian);
            System.out.println("HP стража: " + guardian.getHealth() + "/" + guardian.getMaxHealth());
            System.out.println();
        }
        
        // Підтримка стража
        if (guardian.canUseElementalSupport()) {
            System.out.println("СТРАЖ НАДАЄ ПІДТРИМКУ ПІРОМАНТУ:");
            guardian.elementalSupport(pyro);
            System.out.println();
        }
        
        System.out.println("=== ФІНАЛЬНИЙ СТАН ===");
        System.out.println(pyro.getName() + ": " + pyro.getHealth() + "/" + pyro.getMaxHealth() + " HP");
        System.out.println(tank.getName() + ": " + tank.getHealth() + "/" + tank.getMaxHealth() + " HP");
        System.out.println(mageAss.getName() + ": " + mageAss.getHealth() + "/" + mageAss.getMaxHealth() + " HP");
        System.out.println(guardian.getName() + ": " + guardian.getHealth() + "/" + guardian.getMaxHealth() + " HP");
    }
}