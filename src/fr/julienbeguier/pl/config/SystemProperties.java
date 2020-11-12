package fr.julienbeguier.pl.config;

/**
 * cimer Apache
 */
public class SystemProperties {

	public static final String OS_NAME_WINDOWS_PREFIX = "Windows";
	public static final String OS_NAME_AIX = "AIX";
	public static final String OS_NAME_HP_UX = "HP-UX";
	public static final String OS_NAME_IRIS = "Iris";
	public static final String OS_NAME_Linux = "Linux";
	public static final String OS_NAME_LINUX = "LINUX";
	public static final String OS_NAME_MAC_OS_X = "Mac OS X";
	public static final String OS_NAME_FREEBSD = "FreeBSD";
	public static final String OS_NAME_OPENBSD = "OpenBSD";
	public static final String OS_NAME_NETBSD = "NetBSD";
	public static final String OS_NAME_SOLARIS = "Solaris";
	public static final String OS_NAME_SUN_OS= "SunOS";

	// DATA
	private final String osName;
	private final String userHome;
	private final boolean isUnix;
	private final boolean isWindows;

	public SystemProperties(final String osName, final String userHome) {
		this.osName = osName;
		this.userHome = userHome;
		this.isUnix = oSNameMatchUnix(osName);
		this.isWindows = oSNameMatchWindows(osName);
	}

	public boolean oSNameMatchUnix(final String osName) {
		return osName.startsWith(OS_NAME_AIX) || osName.startsWith(OS_NAME_HP_UX) ||
				osName.startsWith(OS_NAME_IRIS) || osName.startsWith(OS_NAME_Linux) ||
				osName.startsWith(OS_NAME_LINUX) || osName.startsWith(OS_NAME_MAC_OS_X) ||
				osName.startsWith(OS_NAME_FREEBSD) || osName.startsWith(OS_NAME_OPENBSD) ||
				osName.startsWith(OS_NAME_NETBSD) || osName.startsWith(OS_NAME_SOLARIS) ||
				osName.startsWith(OS_NAME_SUN_OS);
	}

	public boolean oSNameMatchWindows(final String osName) {
		return osName.startsWith(OS_NAME_WINDOWS_PREFIX);
	}

	public String getOsName() {
		return this.osName;
	}

	public String getUserHome() {
		return this.userHome;
	}

	public boolean isUnix() {
		return this.isUnix;
	}

	public boolean isWindows() {
		return this.isWindows;
	}
}

