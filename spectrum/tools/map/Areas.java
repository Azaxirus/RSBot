package spectrum.tools.map;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public interface Areas {
	public static Area ZANARIS = new Area(new Tile[] { new Tile(2372, 4471, 0),
			new Tile(2428, 4471, 0), new Tile(2428, 4425, 0),
			new Tile(2372, 4425, 0) });
	public static Area ZANARIS_BANK = new Area(new Tile[] {
			new Tile(2378, 4463, 0), new Tile(2385, 4463, 0),
			new Tile(2385, 4452, 0), new Tile(2378, 4425, 0) });

	public static final Tile[] TRAP_1 = { new Tile(3536, 3450, 0),
			new Tile(3537, 3450, 0), new Tile(3541, 3452, 0),
			new Tile(3541, 3451, 0), new Tile(3535, 3447, 0),
			new Tile(3536, 3447, 0), new Tile(3539, 3446, 0),
			new Tile(3539, 3447, 0) };
	public static final Tile[] TRAP_2 = {
			// SW
			new Tile(3549, 3450, 0), new Tile(3550, 3450, 0),
			// NE
			new Tile(3554, 3453, 0), new Tile(3553, 3453, 0),
			// SE
			new Tile(3552, 3450, 0), new Tile(3553, 3450, 0) };

	public static final Tile[] TRAP_3 = { new Tile(3536, 3450, 0),
			new Tile(3541, 3451, 0), new Tile(3536, 3447, 0),
			new Tile(3539, 3447, 0) };

	public static final Tile[] centralArea = { new Tile(3536, 3448, 0),
			new Tile(3536, 3449, 0), new Tile(3537, 3449, 0),
			new Tile(3538, 3449, 0), new Tile(3539, 3449, 0),
			new Tile(3537, 3448, 0), new Tile(3538, 3448, 0),
			new Tile(3539, 3448, 0), new Tile(3539, 3450, 0),
			new Tile(3538, 3450, 0), new Tile(3540, 3450, 0),
			new Tile(3540, 3449, 0) };
	public static final Tile[] centralArea2 = { new Tile(3552, 3452, 0),
			new Tile(3551, 3452, 0), new Tile(3552, 3451, 0),
			new Tile(3551, 3451, 0), new Tile(3552, 3450, 0),
			new Tile(3551, 3450, 0) };
	// public static final Tile[] centralArea3 = { new Tile(), };
	public static final Tile[] trapNWsurrounding = { new Tile(3535, 3451, 0),
			new Tile(3536, 3451, 0), new Tile(3537, 3451, 0),
			new Tile(3538, 3451, 0), new Tile(3535, 3450, 0),
			new Tile(3536, 3450, 0), new Tile(3537, 3450, 0),
			new Tile(3538, 3450, 0), new Tile(3535, 3449, 0),
			new Tile(3536, 3449, 0), new Tile(3537, 3449, 0),
			new Tile(3538, 3449, 0) };
	public static final Tile[] trapNEsurrounding = { new Tile(3540, 3452, 0),
			new Tile(3541, 3452, 0), new Tile(3542, 3452, 0),
			new Tile(3540, 3451, 0), new Tile(3541, 3451, 0),
			new Tile(3542, 3451, 0), new Tile(3540, 3450, 0),
			new Tile(3541, 3450, 0), new Tile(3542, 3450, 0) };
	public static final Tile[] trapSWsurrounding = { new Tile(3534, 3448, 0),
			new Tile(3535, 3448, 0), new Tile(3536, 3448, 0),
			new Tile(3537, 3448, 0), new Tile(3534, 3447, 0),
			new Tile(3535, 3447, 0), new Tile(3536, 3447, 0),
			new Tile(3537, 3447, 0), new Tile(3534, 3446, 0),
			new Tile(3535, 3446, 0), new Tile(3536, 3446, 0),
			new Tile(3537, 3446, 0) };
	public static final Tile[] trapSEsurrounding = { new Tile(3538, 3448, 0),
			new Tile(3539, 3448, 0), new Tile(3540, 3448, 0),
			new Tile(3538, 3447, 0), new Tile(3539, 3447, 0),
			new Tile(3540, 3447, 0), new Tile(3538, 3446, 0),
			new Tile(3539, 3446, 0), new Tile(3540, 3446, 0) };
	public static final Tile[][] trapAreas1 = { trapNWsurrounding,
			trapNEsurrounding, trapSWsurrounding, trapSEsurrounding };

	public static final Tile[] trapNWsurrounding2 = {};
	public static final Tile[] trapNEsurrounding2 = { new Tile(3554, 3453, 0),
			new Tile(3555, 3453, 0), new Tile(3553, 3453, 0),
			new Tile(3552, 3453, 0), new Tile(3554, 3452, 0),
			new Tile(3555, 3452, 0), new Tile(3553, 3452, 0),
			new Tile(3552, 3452, 0), new Tile(3551, 3452, 0) };
	public static final Tile[] trapSWsurrounding2 = { new Tile(3548, 3449, 0),
			new Tile(3548, 3450, 0), new Tile(3548, 3451, 0),
			new Tile(3549, 3449, 0), new Tile(3549, 3450, 0),
			new Tile(3549, 3451, 0), new Tile(3550, 3449, 0),
			new Tile(3550, 3450, 0), new Tile(3550, 3451, 0),
			new Tile(3551, 3449, 0), new Tile(3551, 3450, 0),
			new Tile(3551, 3451, 0) };
	public static final Tile[] trapSEsurrounding2 = { new Tile(3552, 3450, 0),
			new Tile(3553, 3450, 0), new Tile(3552, 3451, 0),
			new Tile(3553, 3451, 0), new Tile(3554, 3451, 0),
			new Tile(3551, 3449, 0), new Tile(3551, 3450, 0), };
	public static final Tile[][] trapAreas2 = { trapNWsurrounding2,
			trapNEsurrounding2, trapSWsurrounding2, trapSEsurrounding2 };

	public static final Tile[] trapNWsurrounding3 = { new Tile(3535, 3451, 0),
			new Tile(3536, 3451, 0), new Tile(3537, 3451, 0),
			new Tile(3538, 3451, 0), new Tile(3535, 3450, 0),
			new Tile(3536, 3450, 0), new Tile(3537, 3450, 0),
			new Tile(3538, 3450, 0), new Tile(3535, 3449, 0),
			new Tile(3536, 3449, 0), new Tile(3537, 3449, 0),
			new Tile(3538, 3449, 0) };
	public static final Tile[] trapNEsurrounding3 = { new Tile(3540, 3452, 0),
			new Tile(3541, 3452, 0), new Tile(3542, 3452, 0),
			new Tile(3540, 3451, 0), new Tile(3541, 3451, 0),
			new Tile(3542, 3451, 0), new Tile(3540, 3450, 0),
			new Tile(3541, 3450, 0), new Tile(3542, 3450, 0) };
	public static final Tile[] trapSWsurrounding3 = { new Tile(3534, 3448, 0),
			new Tile(3535, 3448, 0), new Tile(3536, 3448, 0),
			new Tile(3537, 3448, 0), new Tile(3534, 3447, 0),
			new Tile(3535, 3447, 0), new Tile(3536, 3447, 0),
			new Tile(3537, 3447, 0), new Tile(3534, 3446, 0),
			new Tile(3535, 3446, 0), new Tile(3536, 3446, 0),
			new Tile(3537, 3446, 0), };
	public static final Tile[] trapSEsurrounding3 = { new Tile(3538, 3448, 0),
			new Tile(3539, 3448, 0), new Tile(3540, 3448, 0),
			new Tile(3538, 3447, 0), new Tile(3539, 3447, 0),
			new Tile(3540, 3447, 0), new Tile(3538, 3446, 0),
			new Tile(3539, 3446, 0), new Tile(3540, 3446, 0) };
	public static final Tile[][] trapAreas3 = { trapNWsurrounding3,
			trapNEsurrounding3, trapSWsurrounding3, trapSEsurrounding3 };
	public static final Area bank = new Area(new Tile[] {
			new Tile(3506, 3484, 0), new Tile(3517, 3484, 0),
			new Tile(3517, 3476, 0), new Tile(3513, 3472, 0),
			new Tile(3506, 3473, 0) });
	public static final Area bottingArea = new Area(new Tile[] {
			new Tile(3483, 3467, 0), new Tile(3482, 3513, 0),
			new Tile(3500, 3525, 0), new Tile(3524, 3525, 0),
			new Tile(3540, 3516, 0), new Tile(3548, 3496, 0),
			new Tile(3546, 3473, 0), new Tile(3551, 3461, 0),
			new Tile(3560, 3454, 0), new Tile(3558, 3449, 0),
			new Tile(3547, 3444, 0), new Tile(3534, 3441, 0),
			new Tile(3536, 3435, 0), new Tile(3530, 3433, 0),
			new Tile(3523, 3432, 0), new Tile(3517, 3431, 0),
			new Tile(3506, 3432, 0), new Tile(3492, 3445, 0) });
	public static Area lizards = new Area(new Tile[] { new Tile(3529, 3455, 0),
			new Tile(3544, 3455, 0), new Tile(3546, 3452, 0),
			new Tile(3546, 3447, 0), new Tile(3542, 3444, 0),
			new Tile(3537, 3442, 0), new Tile(3532, 3443, 0),
			new Tile(3529, 3444, 0), new Tile(3528, 3447, 0) });
	public static final Area area1 = new Area(new Tile[] {
			new Tile(3534, 3453, 0), new Tile(3531, 3449, 0),
			new Tile(3533, 3443, 0), new Tile(3536, 3442, 0),
			new Tile(3543, 3445, 0), new Tile(3545, 3453, 0),
			new Tile(3539, 3454, 0) });
	public static final Area area2 = new Area(new Tile[] {
			new Tile(3546, 3452, 0), new Tile(3546, 3446, 0),
			new Tile(3554, 3447, 0), new Tile(3559, 3452, 0),
			new Tile(3555, 3456, 0), new Tile(3549, 3456, 0) });
	public static final Area area3 = new Area(new Tile[] {
			new Tile(3546, 3434, 0), new Tile(3539, 3434, 0),
			new Tile(3537, 3440, 0), new Tile(3541, 3443, 0),
			new Tile(3546, 3445, 0), new Tile(3551, 3446, 0),
			new Tile(3554, 3446, 0), new Tile(3559, 3446, 0),
			new Tile(3560, 3445, 0), new Tile(3560, 3443, 0),
			new Tile(3558, 3441, 0), new Tile(3555, 3439, 0),
			new Tile(3554, 3438, 0) });
	public final static Area barArea = new Area(new Tile[] {
			new Tile(3484, 3488, 0), new Tile(3520, 3488, 0),
			new Tile(3519, 3475, 0), new Tile(3510, 3467, 0),
			new Tile(3494, 3461, 0), new Tile(3483, 3465, 0),
			new Tile(3481, 3477, 0) });
	public final static Area obelisk = new Area(new Tile[] {
			new Tile(3438, 3500, 0), new Tile(3463, 3499, 0),
			new Tile(3477, 3493, 0), new Tile(3495, 3494, 0),
			new Tile(3499, 3479, 0), new Tile(3488, 3474, 0),
			new Tile(3474, 3466, 0), new Tile(3453, 3465, 0),
			new Tile(3442, 3480, 0) });
}
