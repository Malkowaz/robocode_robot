package vai_brasil;
import robocode.*;
import java.awt.Color;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.*;

// Fiat_uno_93 - a robot by Gabriela Liz Moreira, Gustavo Malkovski e Luiz Kraisch

public class Fiat_uno_93 extends TeamRobot {

    double moveAmount; // A movimentação

	// Movimentação tipo wall
    public void run() {
	
		// Aqui ele calcula o tamanho das paredes para ajustar a movimentação tipo wall
        moveAmount = Math.max(getBattleFieldWidth(),getBattleFieldHeight()); 
		
		setBodyColor(Color.magenta);
		setGunColor(Color.magenta);
		setRadarColor(Color.magenta);
		setBulletColor(Color.magenta);
		setScanColor(Color.magenta);
		
		// Aqui faz com que ele se mova até os limites do campo e vire para o lado oposto sempre.
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        turnRight(90);

        while (true) {
            // Se move para a parede encontrada através da captação do radar
            setTurnRadarRight(10000);
            setAhead(moveAmount);
            waitFor(new MoveCompleteCondition(this));
            // Aqui ele espera até achar uma nova parede e mira para o campo oposto
            turnRight(90);
        }
    }

	// Colisão com outros Robôs
    public void onHitRobot(HitRobotEvent e) {
		// e.getBearing() dá uma variável igual a ângulo no sentido horário da forma como o track do robô esta voltado para o robo
		
        // Aqui se ele acaba batendo no robo, ele recua para trás
        if (e.getBearing() > -90 && e.getBearing() < 90)
            back(100);
        // Aqui se ele estiver atrás o robo vai para a frente.
        else
            ahead(100);
    }

	// Scanner do Robô!!!!
    public void onScannedRobot(ScannedRobotEvent e) {
		//Aqui é feito a adição do tipo Team para não ter teamfire.
		if (isTeammate(e.getName())){
			return;
		}
		
        double bearingToTarget = getHeading() + e.getBearing();
        double botToGun = bearingToTarget - getGunHeading();
		
        turnGunRight(botToGun);
		
        fire(3);
    }
}